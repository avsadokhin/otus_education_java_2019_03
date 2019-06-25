package ru.otus.dbservice;

import ru.otus.entity.Entity;
import ru.otus.executor.DbExecutor;
import ru.otus.executor.DbExecutorImpl;

import java.sql.SQLException;

public class DdlSeviceImpl<T> implements DdlSevice<T> {
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS %s;";

    private Entity entity;
    private DbExecutor<T> executor;

    public <T> DdlSeviceImpl(T t) {
        this.entity = new Entity(t.getClass());
        this.executor = new DbExecutorImpl<>();
    }

    @Override
    public void createMeta(T t) throws SQLException {

    }

    @Override
    public void deleteMeta(T t) throws SQLException {

    }


}
