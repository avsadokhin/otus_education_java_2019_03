package ru.otus;

import ru.otus.web.JettyWebServer;

public class UserWebApplication {
    private final static int PORT = 8080;

    public static void main(String[] args) throws Exception {
        new UserWebApplication().start();
    }

    private void start() throws Exception {
        JettyWebServer jettyServer = new JettyWebServer(PORT);
        jettyServer.startWeb();
    }


}
