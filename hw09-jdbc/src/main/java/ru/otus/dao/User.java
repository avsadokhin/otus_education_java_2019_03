package ru.otus.dao;

import ru.otus.annotation.Column;
import ru.otus.annotation.Id;
import ru.otus.annotation.Size;
import ru.otus.entity.DbType;

public class User {
    @Column(type = DbType.BIGINT)
    @Id(isAutoIncrement = true)
    @Size(min = 20)
    private long id;

    @Column(type = DbType.VARCHAR)
    @Size(min = 34)
    private String name;

    @Column(type = DbType.VARCHAR)
    @Size(min = 3)
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "id: " + getId() + "\n" +
                "name: " + getName() + "\n" +
                "age: " + getAge() + "\n";

    }
}
