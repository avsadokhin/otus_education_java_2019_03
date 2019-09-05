package ru.otus.config;

import org.springframework.stereotype.Repository;
import ru.otus.dbservice.DbServiceHibernateUserImpl;
import ru.otus.messaging.MessageSystemContext;
import ru.otus.messaging.core.Address;

@Repository
public class UserRepositoryImpl extends DbServiceHibernateUserImpl {
    public UserRepositoryImpl(HibernateConfig configuration, MessageSystemContext context) {
        super(configuration.getConfiguration(), context, new Address("DB"));
    }
}
