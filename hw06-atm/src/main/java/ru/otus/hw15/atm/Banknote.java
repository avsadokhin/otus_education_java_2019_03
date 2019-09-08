package ru.otus.hw15.atm;

public enum Banknote {
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);


    private final int value;

    Banknote(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
