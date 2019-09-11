package ru.otus.hw16.frontend.dbservice;

import ru.otus.hw16.frontend.messaging.core.Addressee;

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
