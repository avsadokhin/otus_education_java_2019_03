package ru.otus.dbservice;

import ru.otus.dao.User;
import ru.otus.entity.Entity;
import ru.otus.executor.DbExecutor;
import ru.otus.executor.DbExecutorImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbServiceImpl<T> implements DbService<T> {

    private final DataSource dataSource;
    private DbExecutor<T> executor;
    private Entity<T> entity;

    public DbServiceImpl(DataSource dataSource, Class<? extends T> clazz) {
        this.dataSource = dataSource;
        this.entity = new Entity(clazz);

    }


    @Override
    public void createMeta() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            executor = new DbExecutorImpl<>(dataSource.getConnection());
            String query = entity.getMetaCreateStatment();
            System.out.println(query);
           // executor.createTable("");

        }
    }

    @Override
    public void deleteMeta() throws SQLException {

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
