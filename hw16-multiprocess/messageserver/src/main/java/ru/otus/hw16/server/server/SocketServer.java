package ru.otus.hw16.server.server;


import ru.otus.hw16.server.messaging.core.*;
import ru.otus.hw16.server.workers.MessageWorker;
import ru.otus.hw16.server.workers.SocketMessageWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
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
    private final Map<String, Deque<Address>> clientAddressMap;

    public SocketServer() {
        System.out.println("Start server");
        excecutorService = Executors.newFixedThreadPool(THREADS_COUNT);
        workers = new CopyOnWriteArrayList<>();
        addressMessageWorkerMap = new HashMap<>();
        clientAddressMap = new HashMap<>();
    }


    public void start() throws Exception {
        excecutorService.submit(this::serverMessaging);
        logger.log(Level.INFO, "SocketServer started." + " port:" + PORT + " Waiting for connection...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (!excecutorService.isShutdown()) {
                Socket socket = serverSocket.accept();  //blocks
                SocketMessageWorker worker = new SocketMessageWorker(socket);
                worker.init();
                workers.add(worker);
                logger.log(Level.INFO, "Connection accepted: " + socket);
            }
        }
    }

    private void serverMessaging() {
        while (true) {
            for (MessageWorker worker : workers) {
                 Message message = worker.pool();
                if (message == null) continue;

                if (message instanceof MessageAddressRegistrationRequest) {
                    logger.log(Level.INFO, "Message receipt: " + message.toString());
                    final MessageAddressRegistrationRequest request = (MessageAddressRegistrationRequest) message;
                    final Address address = new Address(request.getAddressName());
                    addressMessageWorkerMap.put(address, worker);
                    clientAddressMap.putIfAbsent(request.getAddressName(), new ConcurrentLinkedDeque<>());
                    clientAddressMap.get(request.getAddressName()).add(address);
                    logger.log(Level.INFO, "Successful address registration in message system: " + address);
                    worker.send(new MessageAddressRegistrationResponse(address));
                    logger.log(Level.INFO, "Address registration response to client...");

                    continue;
                } else if (message instanceof MessageGetAddressRegistrationRequest) {

                    final MessageGetAddressRegistrationRequest request = (MessageGetAddressRegistrationRequest) message;
                    logger.log(Level.INFO, "Get Address registration request to client "+request.getAddressName());
                    if (clientAddressMap.containsKey(request.getAddressName())) {
                        worker.send(new MessageGetAddressRegistrationResponse(clientAddressMap.get(request.getAddressName()).poll()));
                    } else {
                        worker.send(new MessageGetAddressRegistrationResponse(null));
                    }

                } else if (message instanceof AddressMessage) {
                    final AddressMessage request = (AddressMessage) message;
                    if (addressMessageWorkerMap.containsKey(request.getTo())) {
                        addressMessageWorkerMap.get(request.getTo()).send(message);
                    } else {
                        logger.log(Level.WARNING, "Destination address unknown: " + request.getTo());
                    }
                }
                else logger.log(Level.WARNING, "Message type unknown: " + message.getClass());
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean getRunning() {
        return true;
    }

    @Override
    public void setRunning(boolean running) {
        if (!running) {
            excecutorService.shutdown();
        }
    }
}
