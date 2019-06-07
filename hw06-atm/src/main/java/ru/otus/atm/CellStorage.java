package ru.otus.atm;

import java.util.HashMap;
import java.util.Map;

public class CellStorage {
    private Map<Banknote, Integer> cells = new HashMap<>();

    public CellStorage(Map<Banknote, Integer> cells) {
        this.cells = cells;
    }

    public CellStorage() {
        for (Banknote banknote : Banknote.values()) {
            cells.put(banknote, 0);
        }

    }

    public void put(Banknote banknote, int value) {
        int prev_value = 0;
        if (cells.get(banknote) != null) prev_value = cells.get(banknote);

        cells.put(banknote, prev_value + value);
    }

    public Integer getCellBalance (Banknote banknote){
     return cells.get(banknote);
    }


}
