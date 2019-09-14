package ru.otus.hw16.server.server;


import ru.otus.hw16.server.client.MessageServerClient;
import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.Message;
import ru.otus.hw16.server.workers.MessageWorker;
import ru.otus.hw16.server.workers.SocketMessageWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer implements SocketServerMBean {
    private static final Logger logger = Logger.getLogger(SocketServer.class.getName());
    private static final int THREADS_COUNT = 1;
    private static final int PORT = 5050;
    private static final int MIRROR_DELAY_MS = 100;

    private final ExecutorService excecutorService;
    private final List<MessageWorker> workers;
    private final Map<Address, MessageWorker> addressMessageWorkerMap;

    public SocketServer(){
        System.out.println("Start server");
        excecutorService = Executors.newFixedThreadPool(THREADS_COUNT);
        workers = new CopyOnWriteArrayList<>();
        addressMessageWorkerMap = new HashMap<>();
    }


    public void start() throws Exception{
        excecutorService.submit(this::serverMessaging);
        logger.log(Level.INFO,"SocketServer стартован. Ожидание соединений...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            while(!excecutorService.isShutdown()){
                Socket socket = serverSocket.accept();  //blocks
                SocketMessageWorker worker = new SocketMessageWorker(socket);
                worker.init();
                workers.add(worker);
                logger.log(Level.INFO,"Установлено соединение: " + socket);
            }
        }
    }

    private void serverMessaging(){
        while (true){
            for (MessageWorker worker : workers){
                Message message = worker.pool();
                if (message != null){
                    logger.log(Level.INFO,"Получено сообщение: " + message.toString());
                    worker.send(message);
                    message = worker.pool();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean getRunning(){
        return true;
    }

    @Override
    public void setRunning(boolean running){
        if (!running){
            excecutorService.shutdown();
        }
    }
}
