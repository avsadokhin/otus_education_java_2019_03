package ru.otus.hw16.frontend.dao;

import java.io.Serializable;
import java.util.List;


public interface EntityDao<T> {

    void create(T entity);

    void update(T entity);

    <PK extends Serializable> T findById(PK id);

    List<T> findAll();

    void delete(T entity);

    void deleteAll();


}
