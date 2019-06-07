package ru.otus.atm;

import java.util.Map;

public interface Atm {
    public void serviceFillUpCells(Map<Banknote, Integer> cells);
    public void put(MoneyBox moneyBox);
    public void get(Integer value);
}
