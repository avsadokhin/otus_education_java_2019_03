package ru.otus.hw16.server.messaging.core;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private String className;

    public Message(Class<?> cl) {
        className = cl.getName();
    }
 }
