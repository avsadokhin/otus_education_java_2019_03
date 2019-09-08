package ru.otus.hw15.executor;

import java.sql.*;
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
            statement.execute(query);
        }
    }

    @Override
    public void deleteTable(String query) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute(query);
        }
    }


    @Override
    public long insert(String query, List<Object> params) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object p : params) {
                statement.setObject(i++, p);
            }
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else return 0;
            }
        }
    }

    @Override
    public int update(String query, List<Object> params, long id) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object p : params) {
                statement.setObject(i++, p);
            }
            statement.setObject(i++, id);
            statement.executeUpdate();

            return statement.getUpdateCount();


        }
    }

    @Override
    public T select(String query, Function<ResultSet, T> function, long id) throws SQLException {
        try (final PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return function.apply(resultSet);
            }

        }


    }
}

