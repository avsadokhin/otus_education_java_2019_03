package ru.otus.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.otus.dao.EntityDao;
import ru.otus.dao.UserDaoImpl;
import ru.otus.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DbUserHibernateServiceImpl implements DbService {

    private EntityDao entityDao;
    private final SessionFactory sessionFactory;

    public DbUserHibernateServiceImpl(Configuration configuration, EntityDao entityDao) {
        this.sessionFactory = configuration.buildSessionFactory();
        this.entityDao = entityDao;
    }

    private void updateSessionWithTransaction(Consumer<Session> function) {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();

            function.accept(session);
            transaction.commit();
        }
    }


    private <T> T querySessionWithTransaction(Function<Session, T> function) {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final T res = function.apply(session);

            transaction.commit();

            return res;

        }
    }

    @Override
    public void deleteAll() {
        updateSessionWithTransaction(session -> {
            entityDao.setSession(session);
            entityDao.deleteAll();
        });
    }


    @Override
    public void create(Object entity) {
        updateSessionWithTransaction(session -> {
                    entityDao.setSession(session);
                    entityDao.create(entity);
                }
        );

    }

    @Override
    public void update(Object entity) {
        updateSessionWithTransaction(session -> {
            entityDao.setSession(session);
            entityDao.update(entity);
        });

    }

    @Override
    public Object findById(Serializable id) {
        User res;
        res = querySessionWithTransaction(session -> {
            entityDao.setSession(session);
            return (User) entityDao.findById(id);
        });

        return res;
    }

    @Override
    public List<User> findAll() {
        List<?> userList;
        userList = querySessionWithTransaction(session -> {
            entityDao.setSession(session);
            return entityDao.findAll();
        });
        return (List<User>) userList;
    }

    @Override
    public void delete(Object entity) {
        updateSessionWithTransaction(session -> {
            entityDao.setSession(session);
            entityDao.delete(entity);
        });


    }
}
