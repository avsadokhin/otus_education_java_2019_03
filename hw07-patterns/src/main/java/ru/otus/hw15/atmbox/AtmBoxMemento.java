package ru.otus.hw15.atmbox;

import ru.otus.hw15.banknote.BanknoteStorage;

public class AtmBoxMemento {
    private BanknoteStorage storageState;

    AtmBoxMemento(BanknoteStorage storageState) {

        this.storageState = storageState;

    }

    BanknoteStorage getStorageState() {
        return storageState;
    }
}
