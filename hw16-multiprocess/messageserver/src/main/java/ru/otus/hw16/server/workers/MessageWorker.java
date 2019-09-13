package ru.otus.hw16.server.workers;


import ru.otus.hw16.server.messaging.core.Message;

import java.io.IOException;

public interface MessageWorker {
    Message pool();

    void send(Message message);

    Message take() throws InterruptedException;

    void close() throws IOException;
}
