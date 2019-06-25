package ru.otus.dao;

import ru.otus.annotation.Id;

public class Account {
    @Id
    private long id;
    private String type;
    private Number rest;

    public Account(String type, Number rest) {
        this.type = type;
        this.rest = rest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Number getRest() {
        return rest;
    }

    public void setRest(Number rest) {
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "id: " + getId() + "\n" +
                "type: " + getType() + "\n" +
                "age: " + getRest() + "\n";

    }
}
