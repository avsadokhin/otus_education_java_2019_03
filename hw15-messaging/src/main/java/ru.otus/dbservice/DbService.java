package ru.otus.dbservice;

import ru.otus.messaging.core.Addressee;

import java.io.Serializable;
import java.util.List;

public interface DbService<T> extends Addressee {

    void create(T entity);

    void update(T entity);

    <PK extends Serializable> T findById(PK id);

    List<T> findAll();

    void delete(T entity);

    void deleteAll();


}
