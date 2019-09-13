package ru.otus.hw16.backend.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.hw16.backend.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class UserDaoHibernateImpl implements EntityDao<User> {
    private Session session;

    public UserDaoHibernateImpl(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public List<User> findAll() {
        Class type = User.class;

        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        final CriteriaQuery<User> criteria = builder.createQuery(type);
        criteria.from(type);
        final Query<User> query = getSession().createQuery(criteria);
        return query.list();

    }

    @Override
    public void create(User entity) {
        getSession().save(entity);
    }

    @Override
    public void update(User entity) {
        getSession().update(entity);
    }

    @Override
    public User findById(Serializable id) {
        User user = getSession().get(User.class, id);
        return user;
    }

    @Override
    public void delete(User entity) {
        getSession().delete(entity);
    }

    @Override
    public void deleteAll() {
        Class type = User.class;

        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        final CriteriaDelete<User> criteria = builder.createCriteriaDelete(type);
        criteria.from(type);
    }
}
