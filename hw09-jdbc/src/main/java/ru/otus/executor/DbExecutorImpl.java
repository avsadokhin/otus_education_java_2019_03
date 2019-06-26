package ru.otus.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Function;

public class DbExecutorImpl<T> implements DbExecutor<T> {

    private Connection connection;

    public DbExecutorImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void createTable(String query) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeQuery(query);
        }
    }

    @Override
    public void deleteTable(String query) throws SQLException {

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
