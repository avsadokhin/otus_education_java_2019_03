package ru.otus.hw16.server.messaging.core;

import java.io.Serializable;
import java.util.logging.Logger;

public abstract class Message implements Serializable {

    private String className;

    public Message(Class<?> cl) {
        className = cl.getName();
    }
 }
