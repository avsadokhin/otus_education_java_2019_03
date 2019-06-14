package ru.otus.atm;

public class AtmBoxMemento {
    BanknoteStorage storageState;

    public AtmBoxMemento(BanknoteStorage storageState) {

        this.storageState = storageState;

    }

    public BanknoteStorage getStorageState() {
        return storageState;
    }
}
