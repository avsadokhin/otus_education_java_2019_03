package ru.otus.hw16.frontend.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.hw16.frontend.dao.EntityDao;
import ru.otus.hw16.frontend.dao.UserDaoHibernateImpl;
import ru.otus.hw16.frontend.entity.User;
import ru.otus.hw16.frontend.messaging.MessageSystemContext;
import ru.otus.hw16.frontend.messaging.core.Address;
import ru.otus.hw16.frontend.messaging.core.MessageSystem;

import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Repository
public class DbServiceHibernateUserImpl implements DbService {

    private final SessionFactory sessionFactory;
    private final MessageSystemContext context;
    private final Address address;

    @Autowired
    public DbServiceHibernateUserImpl(EntityManagerFactory sessionFactory, MessageSystemContext context, Address address) {
        this.sessionFactory = sessionFactory.unwrap(SessionFactory.class);
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
    public void create(Object entity) {
        updateSessionWithTransaction(session -> {
                    EntityDao entityDao = new UserDaoHibernateImpl(session);
                    entityDao.create(entity);
                }
        );

    }

    @Override
    public void update(Object entity) {
        updateSessionWithTransaction(session -> {
            EntityDao entityDao = new UserDaoHibernateImpl(session);
            entityDao.update(entity);
        });

    }

    @Override
    public Object findById(Serializable id) {
        User res;
        res = querySessionWithTransaction(session -> {
            EntityDao entityDao = new UserDaoHibernateImpl(session);
            return (User) entityDao.findById(id);
        });

        return res;
    }

    @Override
    public List<User> findAll() {
        List<?> userList;
        userList = querySessionWithTransaction(session -> {
            EntityDao entityDao = new UserDaoHibernateImpl(session);
            return entityDao.findAll();
        });
        return (List<User>) userList;
    }

    @Override
    public void delete(Object entity) {
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
