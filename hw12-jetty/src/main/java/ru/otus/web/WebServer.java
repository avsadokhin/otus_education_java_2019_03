package ru.otus.web;

import javassist.tools.web.Webserver;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.web.servlets.*;

import javax.security.auth.login.LoginContext;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServer {
    private int PORT;
    private final static String STATIC = "static";

    public WebServer(int PORT) {
        this.PORT = PORT;
    }

    public void startWeb() throws Exception {

        Server server = createServer(PORT);
        server.start();
        server.join();
    }

    private Server createServer(int port) throws MalformedURLException {
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");
        context.addServlet(new ServletHolder(new UserAdminServlet()), "/userAdmin");

        server.setHandler(new HandlerList(context));

        HandlerList handlers = new HandlerList();

        handlers.setHandlers(new Handler[]{createResourceHandler(), context});
        server.setHandler(handlers);
        return server;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        URL fileDir = Webserver.class.getClassLoader().getResource(STATIC);
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;
    }
}
