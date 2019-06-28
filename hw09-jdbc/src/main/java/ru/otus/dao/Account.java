package ru.otus.dao;

import ru.otus.annotation.Column;
import ru.otus.annotation.Id;
import ru.otus.annotation.Size;
import ru.otus.entity.DbType;

public class Account {
    @Column(type = DbType.BIGINT)
    @Id(isAutoIncrement = true)
    @Size(min = 20)
    private long id;

    @Column(type = DbType.VARCHAR)
    @Size(min = 255)
    private String type;

    @Column(type = DbType.DOUBLE)
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
