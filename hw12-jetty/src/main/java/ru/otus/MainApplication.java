package ru.otus;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import ru.otus.dbservice.DbService;
import ru.otus.dbservice.DbServiceHibernateUserImpl;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;
import ru.otus.web.WebServer;

import javax.persistence.Entity;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Set;

public class MainApplication {
    private final static int PORT = 8080;

    public static void main(String[] args)  throws Exception{
        new MainApplication().start();
    }
    private void start() throws Exception {
        initDatabase();
        WebServer jettyServer = new WebServer(PORT);
        jettyServer.startWeb();
    }

    private void initDatabase() {
        final DbService dbUserService;
        final Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        final User user;

        Set<Class<?>> classEntitySet;
        classEntitySet = new Reflections("ru.otus.entity").getTypesAnnotatedWith(Entity.class);
        classEntitySet.forEach(aClass -> configuration.addAnnotatedClass(aClass));

        dbUserService = new DbServiceHibernateUserImpl(configuration);
        user = new User("Alex", 20);
        user.setAddress(new AddressDataSet("Lomonosova 20"));
        user.setPhoneList(Arrays.asList(new PhoneDataSet("+79031763647"), new PhoneDataSet("+89031763647")));

        dbUserService.create(user);
    }

}
