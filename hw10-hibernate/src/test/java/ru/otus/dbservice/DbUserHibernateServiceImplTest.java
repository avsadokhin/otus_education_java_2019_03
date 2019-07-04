package ru.otus.dbservice;

import org.assertj.core.api.Assertions;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class DbUserHibernateServiceImplTest {

    private DbService<User, Long> dbUserService;
    private Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    private User user_init;


    @BeforeEach
    void init() {
        dbUserService = new DbUserHibernateServiceImpl(configuration);

        user_init = new User("Alex", 20);
        user_init.setAddress(new AddressDataSet("Lomonosova 20"));
        user_init.setPhoneList(Arrays.asList(new PhoneDataSet("+79031763647"), new PhoneDataSet("+89031763647")));

    }

    @AfterEach
    void deleteMeta() {
        dbUserService.deleteMeta();
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

        User userNew = dbUserService.findById(user_init.getId());

        Assertions.assertThat(userNew.getAddress().getStreet().equals(addressExpected.getStreet()));
        Assertions.assertThat(userNew.getPhoneList()).hasSize(3);
    }

    @Test
    void findById() {
        dbUserService.create(user_init);
        User userActual = dbUserService.findById(user_init.getId());
        AddressDataSet addressActual = userActual.getAddress();
        List<PhoneDataSet> phoneActual;
        phoneActual = userActual.getPhoneList();

        Assertions.assertThat(userActual).isEqualToComparingOnlyGivenFields(user_init, "id", "name", "age");
        Assertions.assertThat(addressActual).isEqualToComparingOnlyGivenFields(user_init.getAddress(), "id", "street");
        Assertions.assertThat(phoneActual).hasSize(2);
    }

    @Test
    void delete() {
        dbUserService.delete(user_init);
        List<User> userList = dbUserService.findAll();
        Assertions.assertThat(userList).isEmpty();


    }
}