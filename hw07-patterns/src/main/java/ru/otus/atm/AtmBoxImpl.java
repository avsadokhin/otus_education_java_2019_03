package ru.otus.atm;

import java.util.HashMap;
import java.util.Map;

public class AtmBoxImpl implements AtmBoxObserver {
    private String atmCode;
    private BanknoteStorage banknoteStorage = new BanknoteStorage();
    AtmBoxOriginator originator = new AtmBoxOriginator();
    AtmBoxCaretaker caretaker = new AtmBoxCaretaker();

    public AtmBoxImpl(String atmCode) {
        this.atmCode = atmCode;

    }

    @Override
    public String getAtmCode() {
        return this.atmCode;
    }

    @Override
    public void showBalance() {
        System.out.println("Balance: " + banknoteStorage.getStorageBalance());
    }

    @Override
    public long getBalance() {
        return banknoteStorage.getStorageBalance();
    }

    @Override
    public void showCellsBalanceService() {
        banknoteStorage.getCellMap().forEach((banknote, cell) -> System.out.println("cell " + cell.getBanknote().getValue() + ": " + cell.getCount()));
    }

    @Override
    public void mountStorageService(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
        originator.setState(banknoteStorage);
        caretaker.setMemento(originator.saveState());
    }

    @Override
    public Map<Banknote, Integer> getBanknotes(int amount) throws Exception {
        Map<Banknote, Integer> amountCrate = new HashMap<>();

        if (amount <= 0 || banknoteStorage.getStorageBalance() < amount)
            throw new Exception("Invalid requested operation! Сan not give the specified amount: " + amount);

        int remainingAmount = amount;

        for (Banknote banknote : banknoteStorage.getBanknoteSortedList()) {
            int banknotesInStorage = banknoteStorage.getCell(banknote).getCount();
            int banknoteCount;
            if (remainingAmount < 0) banknoteCount = 0;
            else banknoteCount = Math.min(remainingAmount / banknote.getValue(), banknotesInStorage);

            remainingAmount = remainingAmount - banknoteCount * banknote.getValue();

            if (banknoteCount > 0) {
                amountCrate.put(banknote, banknoteCount);

            }
        }

        if (remainingAmount != 0)
            throw new Exception("Invalid requested amount operation! No banknotes nominal available");
        banknoteStorage.release(amountCrate);

        return amountCrate;
    }

    @Override
    public void putBanknotes(Map<Banknote, Integer> banknotes) {
        banknoteStorage.store(banknotes);
    }

    @Override
    public void resetState() {
        originator.restoreState(caretaker.getMemento());
        banknoteStorage = originator.getStorage();
    }
}
