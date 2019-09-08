package ru.otus.hw15.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.otus.hw15.cache.CacheEngine;
import ru.otus.hw15.dao.EntityDao;
import ru.otus.hw15.dao.UserDaoHibernateImpl;
import ru.otus.hw15.entity.User;
import ru.otus.hw15.messaging.MessageSystemContext;
import ru.otus.hw15.messaging.core.Address;
import ru.otus.hw15.messaging.core.MessageSystem;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DbServiceCacheHibernateUserImpl implements DbService<User> {

    private final SessionFactory sessionFactory;
    private final CacheEngine<Long, User> cache;
    private final MessageSystemContext context;
    private final Address address;

    public DbServiceCacheHibernateUserImpl(Configuration configuration, CacheEngine cache,  MessageSystemContext context, Address address) {
        this.sessionFactory = configuration.buildSessionFactory();
        this.cache = cache;
        this.context = context;
        this.address = address;
        context.setDbAddress(address);
        context.getMessageSystem().registerAddressee(this);
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
            EntityDao entityDao = new UserDaoHibernateImpl(session);
            entityDao.deleteAll();
        });
    }


    @Override
    public void create(User entity) {
        updateSessionWithTransaction(session -> {
                    EntityDao entityDao = new UserDaoHibernateImpl(session);
                    entityDao.create(entity);
                    cache.put(entity.getId(), entity);
                }
        );

    }

    @Override
    public void update(User entity) {
        updateSessionWithTransaction(session -> {
            EntityDao entityDao = new UserDaoHibernateImpl(session);
            entityDao.update(entity);
            cache.put(entity.getId(), entity);

        });

    }

    @Override
    public User findById(Serializable id) {
        User res;
        res = querySessionWithTransaction(session -> {
            EntityDao<User> entityDao = new UserDaoHibernateImpl(session);

            return cache.getAndPutNotExisted((Long) id, entityDao::findById);

        });

        return res;
    }

    @Override
    public List<User> findAll() {
        List<User> userList;
        userList = querySessionWithTransaction(session -> {
            EntityDao<User> entityDao = new UserDaoHibernateImpl(session);

            List<User> users = entityDao.findAll();
            users.forEach(user -> cache.getAndPutNotExisted(user.getId(), aLong -> user ));
            return users;
        });

        return (List<User>) userList;
    }

    @Override
    public void delete(User entity) {
        updateSessionWithTransaction(session -> {
            EntityDao entityDao = new UserDaoHibernateImpl(session);
            entityDao.delete(entity);
        });


    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
