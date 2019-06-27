package ru.otus.executor;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.List;
import java.util.Map;
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
            for (Object  p: params) {
                statement.setObject(i++, p);
                System.out.println(i +" :" +p);
            }
            statement.executeUpdate();
            connection.commit();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else return 0;
            }
        }
    }

    @Override
    public void update(String query, List<String> params, long id) throws SQLException {

    }

    @Override
    public <T1> T1 select(String query, Function<ResultSet, T1> function, long id) throws SQLException {
        return null;
    }
}
