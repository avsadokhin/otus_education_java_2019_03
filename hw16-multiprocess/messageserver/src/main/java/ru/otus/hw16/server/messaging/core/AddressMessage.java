package ru.otus.hw16.server.messaging.core;

import java.util.logging.Logger;

public abstract class AddressMessage extends Message{
    protected final static Logger logger = Logger.getLogger(AddressMessage.class.getName());
    private final Address from;
    private final Address to;

    public AddressMessage(Address from, Address to, Class<?> clazz) {
        super(clazz);
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Addressee addressee);
}
