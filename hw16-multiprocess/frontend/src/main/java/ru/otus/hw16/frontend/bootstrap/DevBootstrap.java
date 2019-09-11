package ru.otus.hw16.frontend.bootstrap;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.otus.hw16.frontend.dbservice.DbService;
import ru.otus.hw16.frontend.entity.AddressDataSet;
import ru.otus.hw16.frontend.entity.PhoneDataSet;
import ru.otus.hw16.frontend.entity.User;

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
