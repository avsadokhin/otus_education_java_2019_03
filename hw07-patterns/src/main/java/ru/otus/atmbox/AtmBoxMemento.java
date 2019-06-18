package ru.otus.atmbox;

import ru.otus.banknote.BanknoteStorage;

public class AtmBoxMemento {
    private BanknoteStorage storageState;

    AtmBoxMemento(BanknoteStorage storageState) {

        this.storageState = storageState;

    }

    BanknoteStorage getStorageState() {
        return storageState;
    }
}
