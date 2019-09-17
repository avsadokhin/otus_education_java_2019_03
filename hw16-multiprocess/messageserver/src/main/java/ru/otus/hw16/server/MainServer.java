package ru.otus.hw16.server;

import ru.otus.hw16.server.runner.ProcessRunner;
import ru.otus.hw16.server.runner.ProcessRunnerImpl;
import ru.otus.hw16.server.server.SocketServer;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainServer {


    private static final String DB_INSTANCE = String.format("./hw16-multiprocess/backend/h2/bin/h2.bat");

    private static final String FRONT_CLIENT_COMMAND_1 = String.format("java -jar ./hw16-multiprocess/frontend/target/front.jar --server.port=%d --host=localhost --port=5050", 8090);
    private static final String FRONT_CLIENT_COMMAND_2 = String.format("java -jar ./hw16-multiprocess/frontend/target/front.jar --server.port=%d --host=localhost --port=5050", 8091);
    private static final String DB_CLIENT_COMMAND_1 = String.format("java -jar ./hw16-multiprocess/backend/target/backend.jar --server.port=0 --host=localhost --port=5050");
    private static final String DB_CLIENT_COMMAND_2 = String.format("java -jar ./hw16-multiprocess/backend/target/backend.jar --server.port=0 --host=localhost --port=5050");

    private static final int CLIENT_START_DELAY_SEC = 2;

    public static void main(String[] args) throws Exception {
        new MainServer().start();
    }

    private void start() throws Exception {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
           //Запуск произвести руками и произвести инициализацию БД через WEB
        // startClient(executorService, DB_INSTANCE);

        startClient(executorService, FRONT_CLIENT_COMMAND_1);
        startClient(executorService, FRONT_CLIENT_COMMAND_2);
        startClient(executorService, DB_CLIENT_COMMAND_1);
        startClient(executorService, DB_CLIENT_COMMAND_2);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("ru.otus:type=Server");

        SocketServer server = new SocketServer();

        mBeanServer.registerMBean(server, objectName);

        server.start();

        executorService.shutdown();
    }

    private void startClient(ScheduledExecutorService executorService, String command) {
        executorService.schedule(() -> {
                    try {
                        ProcessRunner process = new ProcessRunnerImpl();
                        process.start(command);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, CLIENT_START_DELAY_SEC, TimeUnit.SECONDS
        );
    }
}
