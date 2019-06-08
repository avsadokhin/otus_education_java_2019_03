package ru.otus.atm;

import java.util.Map;

public interface AtmBox {
    void fillUpStorageService(BanknoteStorage banknoteStorage);

    void showCellsBalanceService();

    void showBalance();

    Map<Banknote, Integer> getBanknotes(Integer amount) throws Exception;

    void receiveBanknotes(Map<Banknote, Integer> banknotes);
}
