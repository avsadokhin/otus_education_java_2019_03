package ru.otus.hw16.server.messaging.core;

import ru.otus.hw16.server.workers.MessageWorker;

public interface Addressee {
    Address getAddress();

    MessageWorker getMessageWorker();


}
