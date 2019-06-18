package ru.otus.atmbox;

import ru.otus.banknote.BanknoteCell;
import ru.otus.banknote.BanknoteStorage;

import java.util.HashSet;
import java.util.Set;

public class AtmBoxOriginator {
    private BanknoteStorage storage;

    void setState(BanknoteStorage storage) {
        Set<BanknoteCell> cells = new HashSet<>();

        storage.getCellMap().values().forEach(cell -> cells.add(new BanknoteCell(cell.getBanknote(), cell.getCount())));

        this.storage = new BanknoteStorage(cells);

    }

    BanknoteStorage getStorage() {
        return storage;
    }

    AtmBoxMemento saveState() {
        return new AtmBoxMemento(storage);
    }

    void restoreState(AtmBoxMemento memento) {
        this.storage = memento.getStorageState();

    }

}
