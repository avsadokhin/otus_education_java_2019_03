package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.dbservice.DbServiceHibernateUserImpl;
import ru.otus.messaging.MessageSystemContext;
import ru.otus.messaging.core.Address;
import ru.otus.messaging.core.MessageSystem;
import ru.otus.spring.services.backend.HibernateConfig;

@Repository
public class UserRepositoryImpl extends DbServiceHibernateUserImpl {
    public UserRepositoryImpl(HibernateConfig configuration, MessageSystemContext context) {
        super(configuration.getConfiguration(), context, new Address("DB"));
    }
}
