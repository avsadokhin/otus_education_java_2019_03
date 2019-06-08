package ru.otus.atm;

import java.util.HashMap;
import java.util.Map;

public class AtmBoxImpl implements AtmBox {
    private String atmCode;
    private BanknoteStorage banknoteStorage = new BanknoteStorage();


    public AtmBoxImpl(String atmCode) {
        this.atmCode = atmCode;
    }


    @Override
    public void showBalance() {
        System.out.println("Balance: " + banknoteStorage.getBalance());
    }
    @Override
    public void showCellsBalanceService() {
        banknoteStorage.getCells().forEach((banknote, integer) -> System.out.println("cell " + banknote.getValue() + ": " + integer));
    }

    @Override
    public void fillUpStorageService(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
    }

    @Override
    public Map<Banknote, Integer> getBanknotes(Integer amount) throws Exception {
        Map<Banknote, Integer> amountCrate = new HashMap<>();

        if (amount <= 0 || banknoteStorage.getBalance() < amount)
            throw new Exception("Invalid requested operation! Сan not give the specified amount");

        int remainingAmount = amount;

        for (Banknote banknote : banknoteStorage.banknoteSortedList) {
            int cellCount = banknoteStorage.getCellCount(banknote);
            int banknoteCount;
            if (remainingAmount < 0) banknoteCount = 0;
            else banknoteCount = Math.min( remainingAmount / banknote.getValue(), cellCount);

            remainingAmount = remainingAmount - banknoteCount * banknote.getValue();

            if (banknoteCount > 0) {
                amountCrate.put(banknote, banknoteCount);

            }
        }

        if (remainingAmount != 0)
            throw new Exception("Invalid requested amount operation! No banknotes nominal available");


        amountCrate.forEach((banknote, count) -> banknoteStorage.extract(banknote, count));
        return amountCrate;
    }

    @Override
    public void receiveBanknotes(Map<Banknote, Integer> banknotes) {
        banknotes.forEach((banknote, count) -> banknoteStorage.store(banknote, count));
    }
}
