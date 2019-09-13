package ru.otus.hw16.backend.config;

import org.springframework.stereotype.Repository;
import ru.otus.hw16.backend.dbservice.DbServiceHibernateUserImpl;
import ru.otus.hw16.server.messaging.MessageSystemContext;
import ru.otus.hw16.server.messaging.core.Address;

@Repository
public class UserRepositoryImpl extends DbServiceHibernateUserImpl {
    public UserRepositoryImpl(HibernateConfig configuration, MessageSystemContext context) {
        super(configuration.getConfiguration(), context, new Address("DB"));
    }
}
