package ru.otus.dao;

import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.List;

import static org.hibernate.id.PersistentIdentifierGenerator.PK;

public interface EntityDao {
    void setSession(Object param);

    void create(Object entity);

    void update(Object entity);


    <PK extends Serializable> Object findById(PK id);


    List<?> findAll();

    void delete(Object entity);

    void deleteAll();


}
