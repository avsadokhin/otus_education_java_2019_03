package ru.otus.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import ru.otus.dao.UserDaoImpl;
import ru.otus.entity.User;

import javax.naming.OperationNotSupportedException;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.NotSupportedException;
import java.util.List;
import java.util.Set;

public class DbUserHibernateServiceImpl implements DbService<User, Long> {

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS %s;";
    private static UserDaoImpl userDao;
    private final SessionFactory sessionFactory;
    private final Set<Class<?>> classEntitySet;

    public DbUserHibernateServiceImpl(Configuration configuration) {
        classEntitySet = setClassEntitySet();
        sessionFactory = createSessionFactory(configuration);
        userDao = new UserDaoImpl(sessionFactory);

    }

    private SessionFactory createSessionFactory(Configuration configuration) {

        classEntitySet.forEach(aClass -> configuration.addAnnotatedClass(aClass));
        return configuration.buildSessionFactory();
    }

    private Set<Class<?>> setClassEntitySet() {
        Reflections reflections = new Reflections("ru.otus.entity");
        return reflections.getTypesAnnotatedWith(Entity.class);

    }

    @Override
    public void createMeta() {
        throw new UnsupportedOperationException("Operation not supported due h2 auto creation meta");

    }

    @Override
    public void deleteMeta() {
        try (Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            for (Class<?> entity : classEntitySet) {
                String tableName = entity.isAnnotationPresent(Table.class) ? entity.getAnnotation(Table.class).name() : entity.getSimpleName();
                session.createNativeQuery(String.format(DROP_TABLE, tableName)).executeUpdate();
            }
            transaction.commit();
        }
    }

    @Override
    public Long create(User entity) {
        Long res;

        userDao.openSessionWithTransaction();
        res = userDao.create(entity);
        userDao.closeSessionWithTransaction();

        return res;
    }

    @Override
    public void update(User entity) {
        userDao.openSessionWithTransaction();
        userDao.update(entity);
        userDao.closeSessionWithTransaction();
    }

    @Override
    public User findById(Long id) {
        User resUser;

        userDao.openSession();
        resUser = userDao.findById(id);
        userDao.closeSession();

        return resUser;
    }

    @Override
    public List<User> findAll() {
        List<User> resUserList;

        userDao.openSession();
        resUserList = userDao.findAll();
        userDao.closeSession();

        return resUserList;

    }

    @Override
    public void delete(User entity) {

        userDao.openSessionWithTransaction();
        userDao.delete(entity);
        userDao.closeSessionWithTransaction();

    }

    @Override
    public void deleteAll() {
        userDao.openSessionWithTransaction();
        userDao.deleteAll();
        userDao.closeSession();
    }
}
