package ru.otus.entity;

public enum DbType {
    BIGINT ("BIGINT"),
    VARCHAR("VARCHAR");

    private String value;

    private DbType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

