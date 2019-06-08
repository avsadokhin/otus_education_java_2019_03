package ru.otus.atm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BanknoteStorage {
    private Map<Banknote, Integer> cells = new HashMap<>();
    public List<Banknote> banknoteSortedList = Arrays.asList(Banknote.values());


    public BanknoteStorage(Map<Banknote, Integer> cells) {
        this.cells = cells;
        sortBanknotesDescend();

    }

    public BanknoteStorage() {
        for (Banknote banknote : Banknote.values()) {
            cells.put(banknote, 0);
        }
        sortBanknotesDescend();

    }

    private void sortBanknotesDescend() {
        banknoteSortedList.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()) * -1);
    }

    public void store(Banknote banknote, int value) {
        int prev_value = 0;
        if (cells.get(banknote) != null) prev_value = cells.get(banknote);

        cells.put(banknote, prev_value + value);
    }

    public void extract(Banknote banknote, int value) {
        int prev_value = 0;
        if (cells.get(banknote) != null) prev_value = cells.get(banknote);

        cells.put(banknote, prev_value - value);
    }

    public int getCellCount(Banknote banknote) {
        return cells.get(banknote);
    }

    public long getBalance() {
        return cells.entrySet().stream().map(e -> e.getKey().getValue() * e.getValue()).collect(Collectors.summingLong(Integer::longValue));
    }

    public Map<Banknote, Integer> getCells() {
        return cells;
    }

}
