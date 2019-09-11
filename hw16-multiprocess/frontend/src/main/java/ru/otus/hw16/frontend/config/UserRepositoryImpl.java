package ru.otus.hw16.frontend.config;


import ru.otus.hw16.frontend.dbservice.DbServiceHibernateUserImpl;
import ru.otus.hw16.frontend.messaging.MessageSystemContext;
import ru.otus.hw16.frontend.messaging.core.Address;

import javax.persistence.EntityManagerFactory;

public class UserRepositoryImpl extends DbServiceHibernateUserImpl {
    public UserRepositoryImpl(EntityManagerFactory sessionFactory, MessageSystemContext context) {
        super(sessionFactory, context, new Address("DB"));
    }
}
