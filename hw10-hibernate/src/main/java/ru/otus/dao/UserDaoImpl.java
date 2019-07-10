package ru.otus.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class UserDaoImpl implements EntityDao {
    private Session session;

    public UserDaoImpl() {

    }

    @Override
    public void setSession(Object Session) {
        if (Session instanceof Session) {
            this.session = (Session) Session;
        }

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
    public void create(Object entity) {
        getSession().save(entity);
    }

    @Override
    public void update(Object entity) {
        getSession().update(entity);
    }

    @Override
    public Object findById(Serializable id) {
        User user = getSession().get(User.class, id);
        return user;
    }

    @Override
    public void delete(Object entity) {
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
