package ru.otus.hw16.server.client;

import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.workers.MessageWorker;

import java.io.IOException;

public interface MessageServerClient {
    Address registerFromRequest(String prefix);

    Address registerToRequest(String prefix);

    void startMessaging();

    MessageWorker getSocketMessageWorker();

    Address getAddressTo();
    Address getAddressFrom();

    void close() throws IOException;
}
