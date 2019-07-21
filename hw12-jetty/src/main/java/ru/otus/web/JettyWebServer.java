package ru.otus.web;

import javassist.tools.web.Webserver;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import ru.otus.web.servlets.*;

import javax.persistence.Entity;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class JettyWebServer {
    private int PORT;
    private final static String RESOURCE_STATIC = "static";
    private final static String HIBERNATE_CFG = "hibernate.cfg.xml";

    private final Configuration dbConfig;

    public JettyWebServer(int PORT) {
        this.PORT = PORT;
        this.dbConfig = getDbConfig();
    }

    private Configuration getDbConfig(){
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG);
        Set<Class<?>> classEntitySet;
        classEntitySet = new Reflections("ru.otus.entity").getTypesAnnotatedWith(Entity.class);
        classEntitySet.forEach(aClass -> configuration.addAnnotatedClass(aClass));
        return  configuration;
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
        context.addServlet(new ServletHolder(new UserAdminServlet(dbConfig, new TemplateProcessor())), "/userAdmin");

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

        URL fileDir = Webserver.class.getClassLoader().getResource(RESOURCE_STATIC);
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;


    }





}
