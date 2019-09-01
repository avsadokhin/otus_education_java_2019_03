package ru.otus.dbservice;

import java.io.Serializable;
import java.util.List;

public interface DbService<T> {

    void create(T entity);

    void update(T entity);

    <PK extends Serializable> T findById(PK id);

    List<T> findAll();

    void delete(T entity);

    void deleteAll();


}
