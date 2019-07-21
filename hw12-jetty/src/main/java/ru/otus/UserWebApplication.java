package ru.otus;

import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import ru.otus.dbservice.DbService;
import ru.otus.dbservice.DbServiceHibernateUserImpl;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;
import ru.otus.web.JettyWebServer;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Set;

public class UserWebApplication {
    private final static int PORT = 8080;

    public static void main(String[] args)  throws Exception{
        new UserWebApplication().start();
    }
    private void start() throws Exception {
      //  prepareTestDataDb();
        JettyWebServer jettyServer = new JettyWebServer(PORT);
        jettyServer.startWeb();
    }

  /*  private void prepareTestDataDb() {
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

    }*/

}
