package ru.otus.hw16.server.messaging.messages;


import ru.otus.hw16.server.messaging.core.Address;
import ru.otus.hw16.server.messaging.core.MessageJsonType;

import java.util.logging.Level;

public class MessageCreateUserRequest extends MessageJsonType {
    public MessageCreateUserRequest(Address from, Address to, String jsonMessage, Class<?> clazz) {
        super(from, to, clazz, jsonMessage);
    }
}
