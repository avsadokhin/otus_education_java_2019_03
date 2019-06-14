package ru.otus.atm;

import java.util.HashSet;
import java.util.Set;

public class AtmBoxOriginator {
    private BanknoteStorage storage;

    public void setState(BanknoteStorage storage) {
        Set<BanknoteCell> cells = new HashSet<>();

        storage.getCellMap().values().forEach(cell -> cells.add(new BanknoteCell(cell.getBanknote(), cell.getCount())));

        this.storage = new BanknoteStorage(cells);

    }

    public BanknoteStorage getStorage() {
        return storage;
    }

    public AtmBoxMemento saveState() {
        return new AtmBoxMemento(storage);
    }

    public void restoreState(AtmBoxMemento memento) {
        this.storage = memento.getStorageState();
    }

}
