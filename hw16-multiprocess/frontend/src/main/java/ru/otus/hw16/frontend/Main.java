package ru.otus.hw16.frontend;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.otus.hw16.frontend.messaging_client.FrontMessageServerClient;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class Main {
    private static final Logger logger = Logger.getLogger(ru.otus.hw16.frontend.Main.class.getName());
    @Value("${host}")
    private String host;
    @Value("${port}")
    private int port;


    public static void main(String[] args) {
        logger.log(Level.INFO, "Starting Front service...");
        ConfigurableApplicationContext context = SpringApplication.run(ru.otus.hw16.frontend.Main.class, args);

        FrontMessageServerClient client = context.getBean(FrontMessageServerClient.class);
        client.startMessaging();
    }


    @Bean
    public FrontMessageServerClient getFrontMessageServerClient() {

        return new FrontMessageServerClient(host, port, "FRONT", "DB");

    }

}
