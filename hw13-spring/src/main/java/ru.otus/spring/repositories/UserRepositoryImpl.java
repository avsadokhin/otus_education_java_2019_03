package ru.otus.spring.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.dbservice.DbServiceHibernateUserImpl;
import ru.otus.spring.services.HibernateConfig;

@Repository
public class UserRepositoryImpl extends DbServiceHibernateUserImpl {
    public UserRepositoryImpl(HibernateConfig configuration) {
       super(configuration.getConfiguration());
    }
}
