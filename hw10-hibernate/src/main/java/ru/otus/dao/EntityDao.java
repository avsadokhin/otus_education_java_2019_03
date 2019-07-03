package ru.otus.dao;

import java.io.Serializable;
import java.util.List;

public interface EntityDao<T, PK extends Serializable> {
    public PK create(T entity);
    public void update(T entity);

    public T findById(PK id);
    public List<T> findAll();

    public void delete(T entity);
    public void deleteAll();



}
