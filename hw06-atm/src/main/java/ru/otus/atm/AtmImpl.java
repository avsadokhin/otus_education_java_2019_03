package ru.otus.atm;

import java.util.HashMap;
import java.util.Map;

public class AtmImpl implements Atm {
    CellStorage cellStorage = new CellStorage();

    public AtmImpl(CellStorage cellStorage) {
        this.cellStorage = cellStorage;
    }

    public AtmImpl() {
    }



    @Override
    public void serviceFillUpCells(Map<Banknote, Integer> cells) {

    }

    @Override
    public void put(MoneyBox moneyBox) {

    }

    @Override
    public void get(Integer value) {

    }
}
