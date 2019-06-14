package ru.otus.atm;

import java.util.*;
import java.util.stream.Collectors;

public class BanknoteStorage {
    private Map<Banknote, BanknoteCell> cellMap = new HashMap<>();

    private List<Banknote> banknoteSortedList = new ArrayList<>();


    public BanknoteStorage(Set<BanknoteCell> cells) {
        cells.forEach(banknoteCell -> this.cellMap.put(banknoteCell.getBanknote(), banknoteCell));
        cells.forEach(cell -> banknoteSortedList.add(cell.getBanknote()));

        sortBanknotesDescend();

    }

    public BanknoteStorage() {
        for (Banknote banknote : Banknote.values()) {
            cellMap.put(banknote, new BanknoteCell(banknote, 0));
            banknoteSortedList.add(banknote);
        }

        sortBanknotesDescend();

    }


    private void sortBanknotesDescend() {
        banknoteSortedList.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()) * -1);
    }


    public void store(Map<Banknote, Integer> banknotes) {
        banknotes.forEach((banknote, count) ->
                {
                    try {
                        putInCell(cellMap.get(banknote), count);
                    } catch (Exception e) {
                        System.out.println("Invalid requested operation! Cannot store banknote: " + banknote.getValue());
                    }
                }
        );

    }


    public void putInCell(BanknoteCell cell, int count) {
        cell.putBanknote(count);
    }

    public void release(Map<Banknote, Integer> banknotes) {
        banknotes.forEach((banknote, count) -> getFromCell(cellMap.get(banknote), count));

    }

    public void getFromCell(BanknoteCell cell, int count) {
        cell.getBanknote(count);
    }


    public BanknoteCell getCell(Banknote banknote) {
        return cellMap.get(banknote);
    }

    public long getStorageBalance() {
        return cellMap.entrySet().stream().map(e -> e.getKey().getValue() * e.getValue().getCount()).collect(Collectors.summingLong(Integer::longValue));


    }

    public Map<Banknote, BanknoteCell> getCellMap() {
        return cellMap;
    }

    public List<Banknote> getBanknoteSortedList() {
        return banknoteSortedList;
    }
}
