package ru.otus.hw15.messaging.core;

public interface Addressee {
    Address getAddress();

    MessageSystem getMS();
}
