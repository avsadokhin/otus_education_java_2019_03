package ru.otus.hw16.backend;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import ru.otus.hw16.backend.dbservice.DbService;
import ru.otus.hw16.backend.entity.User;
import ru.otus.hw16.backend.messaging_client.DbMessageServerClientImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    @Value("${host}")
    private String host;
    @Value("${port}")
    private int port;
    @Value("${clientId}")
    private String clientId;


    public static void main(String[] args) {
        logger.log(Level.INFO, "Starting DB service...");
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        DbMessageServerClientImpl client = context.getBean(DbMessageServerClientImpl.class);
        client.startMessaging();
    }


    @Bean
    public DbMessageServerClientImpl getDbMessageServerClient(DbService<User> dbService) {

        return new DbMessageServerClientImpl(host, port,clientId, "FRONT", dbService);

    }

}
