package ru.otus.dbservice;

import java.sql.SQLException;

public interface DbService<T> {
    void create(T t) throws SQLException;
    void update(T t) throws SQLException;

    T load(long id, Class<T> tClass) throws SQLException;
}
