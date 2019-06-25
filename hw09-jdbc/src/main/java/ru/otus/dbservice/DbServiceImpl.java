package ru.otus.dbservice;

import ru.otus.dao.User;
import ru.otus.executor.DbExecutor;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DbServiceImpl<T> implements DbService<T> {

    private final DataSource dataSource;
    private final DbExecutor<T> executor;

    public DbServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.executor = null;
    }


    @Override
    public void create(T t) throws SQLException {

    }

    @Override
    public void update(T t) throws SQLException {

    }

    @Override
    public T load(long id, Class<T> tClass) throws SQLException {
        return null;
    }
}
