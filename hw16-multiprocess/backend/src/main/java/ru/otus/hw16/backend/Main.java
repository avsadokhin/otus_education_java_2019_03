package ru.otus.hw16.backend;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.otus.hw16.backend.dbservice.DbService;
import ru.otus.hw16.model.entity.User;
import ru.otus.hw16.backend.messaging_client.DbMessageServerClientImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
//@EntityScan({"ru.otus.hw16.model.entity"})
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
      @Value("${host}")
    private String host ;
      @Value("${port}")
    private int port;

    public static void main(String[] args) {
        logger.log(Level.INFO, "Starting DB service...");
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        DbMessageServerClientImpl client = context.getBean(DbMessageServerClientImpl.class);
        client.startMessaging();
    }


    @Bean
    public DbMessageServerClientImpl getDbMessageServerClient(DbService<User> dbService) {

        return new DbMessageServerClientImpl(host, port,"DB", "FRONT", dbService);

    }

}
