package ru.otus.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.otus.dbservice.HibernateSessionManager;
import ru.otus.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class UserDaoImpl implements EntityDao {
    private SessionFactory sessionFactory;

    public UserDaoImpl() {

    }


    @Override
    public void initiate(Object sessionFactory) {
        if (sessionFactory instanceof SessionFactory) {
            this.sessionFactory = (SessionFactory) sessionFactory;
        }

    }

    @Override
    public List<User> findAll() {
        Class type = User.class;

        return HibernateSessionManager.querySessionWithTransaction(sessionFactory, session1 -> {
            CriteriaBuilder builder = session1.getCriteriaBuilder();
            final CriteriaQuery<User> criteria = builder.createQuery(type);
            criteria.from(type);
            final Query<User> query = session1.createQuery(criteria);
            return query.list();
        });


    }

    @Override
    public Long create(Object entity) {
        HibernateSessionManager.updateSessionWithTransaction(sessionFactory, session1 -> session1.save(entity));

        return ((User) entity).getId();
    }

    @Override
    public void update(Object entity) {
        HibernateSessionManager.updateSessionWithTransaction(sessionFactory, session1 -> session1.update(entity));
    }

    @Override
    public Object findById(Serializable id) {
        User user = HibernateSessionManager.querySessionWithTransaction(sessionFactory, session1 -> session1.get(User.class, id));
        return user;
    }

    @Override
    public void delete(Object entity) {
        HibernateSessionManager.updateSessionWithTransaction(sessionFactory, session1 -> session1.delete(entity));
    }

    @Override
    public void deleteAll() {
        Class type = User.class;

        HibernateSessionManager.updateSessionWithTransaction(sessionFactory, session1 ->
                {
                    CriteriaBuilder builder = session1.getCriteriaBuilder();
                    final CriteriaDelete<User> criteria = builder.createCriteriaDelete(type);
                    criteria.from(type);
                }
        );


    }
}
