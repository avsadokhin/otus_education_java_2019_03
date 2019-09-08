package ru.otus.hw15.atmbox;

import ru.otus.hw15.banknote.Banknote;
import ru.otus.hw15.banknote.BanknoteStorage;

import java.util.Map;

public interface AtmBox {
    String getAtmCode();

    void mountStorageService(BanknoteStorage banknoteStorage);

    void showCellsBalanceService();

    void showBalance();

    long getBalance();

    Map<Banknote, Integer> getBanknotes(int amount) throws Exception;

    void putBanknotes(Map<Banknote, Integer> banknotes);
}
