package ru.otus.dbservice;

import ru.otus.dao.EntityDao;

import java.io.Serializable;
import java.util.List;

public interface DbService {

    void create(Object entity);

    void update(Object entity);

    <PK extends Serializable> Object findById(PK id);

    List<?> findAll();

    void delete(Object entity);

    void deleteAll();


}
