package ru.otus.hw16.server.messaging;

import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.workers.SocketMessageWorker;

public class MessageSystemContext {


    private final SocketMessageWorker socketMessageWorker;

    private Address frontAddress;
    private Address dbAddress;

    public MessageSystemContext(SocketMessageWorker socketMessageWorker) {
        this.socketMessageWorker = socketMessageWorker;
    }

    public SocketMessageWorker getSocketMessageWorker() {
        return socketMessageWorker;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }

    public void setDbAddress(Address dbAddress) {
        this.dbAddress = dbAddress;
    }

}
