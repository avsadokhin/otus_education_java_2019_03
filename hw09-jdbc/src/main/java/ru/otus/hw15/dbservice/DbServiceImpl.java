package ru.otus.hw15.dbservice;

import ru.otus.hw15.entity.Entity;
import ru.otus.hw15.entity.EntityException;
import ru.otus.hw15.executor.DbExecutor;
import ru.otus.hw15.executor.DbExecutorImpl;
import ru.otus.hw15.reflection.ReflectionHelper;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
            executor = new DbExecutorImpl<>(connection);
            String query = entity.getMetaCreateStatment();
            executor.createTable(query);

        }
    }

    @Override
    public void deleteMeta() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            executor = new DbExecutorImpl<>(connection);
            String query = entity.getMetaDeleteStatment();
            executor.createTable(query);

        }
    }


    @Override
    public void create(T t) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            executor = new DbExecutorImpl<>(connection);
            String query = entity.getInsertPreparedStatement();

            List<Object> fieldValueList = entity.getInsertFieldListValue(t);
            long id = executor.insert(query, fieldValueList);

            connection.commit();

            ReflectionHelper.setFieldValue(t, entity.getFieldId(), id);
        }


    }

    @Override
    public void update(T t) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            executor = new DbExecutorImpl<>(connection);
            String query = entity.getUpdatePreparedStatement();

            List<Object> fieldValueList = entity.getUpdateFieldListValue(t);

            Field fieldId = entity.getFieldId();
            Object valueId = ReflectionHelper.getFieldValue(t, fieldId);

            long id = valueId != null ? (long) valueId : -1;
            if (id == -1) throw new EntityException("Value for ID column must be set");

            int updatedCnt = executor.update(query, fieldValueList, id);

            if (updatedCnt == 0)
                throw new EntityException("Entity \"" + entity.getTableName() + "\" with id = " + id + " doesn't exists");

            connection.commit();

        }

    }

    @Override
    public T load(long id, Class<T> clazz) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            executor = new DbExecutorImpl<>(connection);
            String query = entity.getSelectByIdPreparedStatement();

            Function<ResultSet, T> function;
            function = resultSet -> {

                List<T> resultObjectList;
                try {
                    resultObjectList = getObjectList(resultSet, clazz);
                    if (resultObjectList.size() > 1) throw new EntityException("Not unique entity returned by id");
                    return resultObjectList.size() > 0 ? resultObjectList.get(0) : null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return null;

            };
            return executor.select(query, function, id);
        }

    }

    private <T> List<T> getObjectList(ResultSet resultSet, Class<T> clazz) throws SQLException {
        List<T> reultObjects = new ArrayList<>();
        final List<Field> objectFieldsList = ReflectionHelper.getObjectFieldsList(clazz);

        while (resultSet.next()) {
            T resultObject = ReflectionHelper.instantiate(clazz);
            for (Field field : objectFieldsList) {

                Object resultSetObject;
                resultSetObject = resultSet.getObject(field.getName());

                ReflectionHelper.setFieldValue(resultObject, field, resultSetObject);
            }
            reultObjects.add(resultObject);
        }
        return reultObjects;
    }

}
