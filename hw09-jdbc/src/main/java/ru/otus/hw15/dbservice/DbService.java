package ru.otus.hw15.dbservice;

import java.sql.SQLException;

public interface DbService<T> {
    void createMeta () throws SQLException;
    void deleteMeta () throws SQLException;
    void create(T t) throws SQLException;
    void update(T t) throws SQLException;

    T load(long id, Class<T> clazz) throws SQLException;
}
