package ru.otus.hw15.config;


import ru.otus.hw15.dbservice.DbServiceHibernateUserImpl;
import ru.otus.hw15.messaging.MessageSystemContext;
import ru.otus.hw15.messaging.core.Address;

import javax.persistence.EntityManagerFactory;

public class UserRepositoryImpl extends DbServiceHibernateUserImpl {
    public UserRepositoryImpl(EntityManagerFactory sessionFactory, MessageSystemContext context) {
        super(sessionFactory, context, new Address("DB"));
    }
}
