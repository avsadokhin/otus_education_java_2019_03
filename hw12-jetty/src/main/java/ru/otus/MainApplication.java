package ru.otus;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.MalformedURLException;

public class MainApplication {
    private final static int PORT = 8080;

    public static void main(String[] args)  throws Exception{
        new MainApplication().startWeb();
    }
    private void startWeb() throws Exception {
        Server server = createServer(PORT);
        server.start();
        server.join();
    }

    public Server createServer(int port) throws MalformedURLException {

        return null;
    }

}
