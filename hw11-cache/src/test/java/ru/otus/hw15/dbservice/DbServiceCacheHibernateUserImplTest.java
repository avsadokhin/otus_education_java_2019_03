package ru.otus.hw15.dbservice;

import org.assertj.core.api.Assertions;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import ru.otus.hw15.cache.CacheEngine;
import ru.otus.hw15.cache.CacheEngineTimerBased;
import ru.otus.hw15.entity.AddressDataSet;
import ru.otus.hw15.entity.PhoneDataSet;
import ru.otus.hw15.entity.User;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


class DbServiceCacheHibernateUserImplTest {

    private DbService dbUserService;
    private Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    private User user_init;


    @BeforeEach
    void init() {
        Set<Class<?>> classEntitySet;
        classEntitySet = new Reflections("ru.otus.entity").getTypesAnnotatedWith(Entity.class);
        classEntitySet.forEach(aClass -> configuration.addAnnotatedClass(aClass));

        CacheEngine cacheEngine = new CacheEngineTimerBased(10,10000,0);

        dbUserService = new DbServiceCacheHibernateUserImpl(configuration, cacheEngine);
        user_init = new User("Alex", 20);
        user_init.setAddress(new AddressDataSet("Lomonosova 20"));
        user_init.setPhoneList(Arrays.asList(new PhoneDataSet("+79031763647"), new PhoneDataSet("+89031763647")));

    }


    @Test
    void create() {
        dbUserService.create(user_init);
        Assertions.assertThat(user_init.getId() > 0);

    }

    @Test
    void update() {
        dbUserService.create(user_init);


        AddressDataSet addressExpected = new AddressDataSet("Mira 20");
        addressExpected.setId(user_init.getAddress().getId());

        List<PhoneDataSet> phoneExpected = new ArrayList<>(Arrays.asList(new PhoneDataSet("123")));


        user_init.setAddress(addressExpected);
        user_init.setPhoneList(phoneExpected);
        dbUserService.update(user_init);

        User userNew = (User) dbUserService.findById(user_init.getId());

        Assertions.assertThat(userNew.getAddress().getStreet().equals(addressExpected.getStreet()));
        Assertions.assertThat(userNew.getPhoneList()).hasSize(1);
    }

    @Test
    void findById() {
        dbUserService.create(user_init);
        User userActual = (User) dbUserService.findById(user_init.getId());
        AddressDataSet addressActual = userActual.getAddress();
        List<PhoneDataSet> phoneActual;
        phoneActual = userActual.getPhoneList();

        Assertions.assertThat(userActual).isEqualToComparingOnlyGivenFields(user_init, "id", "name", "age");
        Assertions.assertThat(addressActual).isEqualToComparingOnlyGivenFields(user_init.getAddress(), "id", "street");
        Assertions.assertThat(phoneActual).hasSize(2);
    }

    @Test
    void delete() {
        dbUserService.create(user_init);

        dbUserService.delete(user_init);
        List<User> userList = (List<User>) dbUserService.findAll();
        Assertions.assertThat(userList).isEmpty();


    }
}