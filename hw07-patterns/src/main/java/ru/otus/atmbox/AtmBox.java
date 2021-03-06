package ru.otus.atmbox;

import ru.otus.banknote.Banknote;
import ru.otus.banknote.BanknoteStorage;

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
