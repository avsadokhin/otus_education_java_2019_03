package ru.otus.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.otus.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.util.List;

public class UserDaoImpl implements EntityDao<User, Long> {
    private Session session;
    private Transaction transaction;
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Session openSession() {
        session = sessionFactory.openSession();
        return session;
    }

    public Session openSessionWithTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        return session;
    }
    public void closeSessionWithTransaction() {
        transaction.commit();
        session.close();
    }

    public void closeSession() {
        session.close();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Long create(User entity) {
        getSession().save(entity);
        return entity.getId();
    }

    @Override
    public void update(User entity) {
        getSession().update(entity);

    }

    @Override
    public User findById(Long id) {
        User user = (User) getSession().get(User.class, id);
        return user;
    }

    @Override
    public List<User> findAll() {
        Class type = User.class;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        final CriteriaQuery<User> criteria = builder.createQuery(type);
        criteria.from(type);
        final Query<User> query = session.createQuery(criteria);
        return query.list();
    }

    @Override
    public void delete(User entity) {
        session.delete(entity);
    }

    @Override
    public void deleteAll() {
        Class type = User.class;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        final CriteriaDelete<User> criteria = builder.createCriteriaDelete(type);
        criteria.from(type);
    }
}
