package ru.otus.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public class DbExecutorImpl<T> implements DbExecutor<T> {
    @Override
    public void createTable(T t) throws SQLException {

    }

    @Override
    public void deleteTable(T t) throws SQLException {

    }

    @Override
    public long insert(String query, List<String> params) throws SQLException {
        return 0;
    }

    @Override
    public void update(String query, List<String> params, long id) throws SQLException {

    }

    @Override
    public <T1> T1 select(String query, Function<ResultSet, T1> function, long id) throws SQLException {
        return null;
    }
}
