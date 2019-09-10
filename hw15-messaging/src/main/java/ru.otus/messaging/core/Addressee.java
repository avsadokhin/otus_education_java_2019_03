package ru.otus.messaging.core;

public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
