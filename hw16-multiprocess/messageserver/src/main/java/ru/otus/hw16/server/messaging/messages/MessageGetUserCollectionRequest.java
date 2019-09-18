package ru.otus.hw16.server.messaging.messages;

import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.AddressMessage;
import ru.otus.hw16.server.messaging.core.Addressee;

public class MessageGetUserCollectionRequest extends AddressMessage {
    public MessageGetUserCollectionRequest(Address from, Address to) {
        super(from, to, MessageGetUserCollectionRequest.class);
    }

    @Override
    public void exec(Addressee addressee) {

    }
}
