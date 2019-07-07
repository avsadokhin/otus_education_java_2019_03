package ru.otus.dbservice;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.dao.EntityDao;

import java.io.Serializable;
import java.util.List;

public class DbHibernateServiceImpl implements DbService {

    private EntityDao entityDao;
    private final SessionFactory sessionFactory;

    public DbHibernateServiceImpl(Configuration configuration, EntityDao entityDao) {
        this.sessionFactory = configuration.buildSessionFactory();
        this.entityDao = entityDao;
        this.entityDao.initiate(sessionFactory);

    }


    @Override
    public void deleteAll() {
        entityDao.deleteAll();
    }


    @Override
    public Serializable create(Object entity) {
        Serializable res;
        res = entityDao.create(entity);
        return res;
    }

    @Override
    public void update(Object entity) {
        entityDao.update(entity);
    }

    @Override
    public Object findById(Serializable id) {
        return entityDao.findById(id);
    }

    @Override
    public List<?> findAll() {
        return entityDao.findAll();
    }

    @Override
    public void delete(Object entity) {
        entityDao.delete(entity);

    }
}
