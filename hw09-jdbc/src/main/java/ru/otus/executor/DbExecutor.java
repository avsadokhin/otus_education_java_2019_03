package ru.otus.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public interface DbExecutor<T> {

    void createTable(String query) throws SQLException;

    void deleteTable(String query) throws SQLException;

    long insert(String query, List<Object> params) throws SQLException;

    int update(String query, List<Object> params, long id) throws SQLException;

    T select(String query, Function<ResultSet, T> function, long id) throws SQLException;
}