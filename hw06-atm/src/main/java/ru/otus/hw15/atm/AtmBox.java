package ru.otus.hw15.atm;

import java.util.Map;

public interface AtmBox {
    void mountStorageService(BanknoteStorage banknoteStorage);

    void showCellsBalanceService();

    void showBalance();

    Map<Banknote, Integer> getBanknotes(int amount) throws Exception;

    void putBanknotes(Map<Banknote, Integer> banknotes);
}
