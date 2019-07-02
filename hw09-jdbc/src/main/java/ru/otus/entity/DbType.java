package ru.otus.entity;

public enum DbType {
    INT("INT"),
    BIGINT("BIGINT"),
    VARCHAR("VARCHAR"),
    DOUBLE("DOUBLE");

    private String value;

    DbType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

