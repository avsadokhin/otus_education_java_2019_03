package ru.otus.spring.services;

import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;
import javax.persistence.Entity;
import java.util.Set;


@Service
public class HibernateConfigImpl implements HibernateConfig {
    private final static String HIBERNATE_CFG = "hibernate.cfg.xml";

    @Override
    public Configuration getConfiguration()  {
        Configuration configuration;
        configuration = new Configuration().configure(HIBERNATE_CFG);
        Set<Class<?>> classEntitySet;
        classEntitySet = new Reflections("ru.otus.entity").getTypesAnnotatedWith(Entity.class);
        classEntitySet.forEach(aClass -> configuration.addAnnotatedClass(aClass));
        return configuration;
    }

}
