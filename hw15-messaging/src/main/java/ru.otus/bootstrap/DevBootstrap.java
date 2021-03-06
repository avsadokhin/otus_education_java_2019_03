package ru.otus.bootstrap;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.otus.dbservice.DbService;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;

import java.util.Arrays;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private DbService<User> repository;

    public DevBootstrap(DbService<User> repository) {
        this.repository = repository;
      }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        final User user;
        user = new User("Alex", 20);
        user.setAddress(new AddressDataSet("Lomonosova 20"));
        user.setPhoneList(Arrays.asList(new PhoneDataSet("+79031763647"), new PhoneDataSet("+89031763647")));

        repository.create(user);
    }
}
