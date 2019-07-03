package ru.otus.dbservice;

import java.io.Serializable;
import java.util.List;

public interface DbService<T, PK extends Serializable> {
    void createMeta ();
    void deleteMeta ();
    public PK create(T entity);
    public void update(T entity);

    public T findById(PK id);
    public List<T> findAll();

    public void delete(T entity);
    public void deleteAll();

}
