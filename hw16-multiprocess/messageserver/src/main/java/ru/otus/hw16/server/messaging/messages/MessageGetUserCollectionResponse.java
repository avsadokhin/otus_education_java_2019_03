package ru.otus.hw16.server.messaging.messages;

import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.MessageJsonType;

public class MessageGetUserCollectionResponse extends MessageJsonType {
    public MessageGetUserCollectionResponse(Address from, Address to, String jsonMessage, Class<?> clazz) {
        super(from, to, clazz, jsonMessage);
    }
}
