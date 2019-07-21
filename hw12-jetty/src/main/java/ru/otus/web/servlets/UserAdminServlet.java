package ru.otus.web.servlets;

import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import ru.otus.dbservice.DbService;
import ru.otus.dbservice.DbServiceHibernateUserImpl;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;

import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.otus.web.TemplateProcessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserAdminServlet extends HttpServlet {
    public static String pathSpec = "/userAdmin";
    private final TemplateProcessor templateProcessor;
    DbService<User> dbUserService;

    public UserAdminServlet(Configuration dbConfig, TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
        this.dbUserService = new DbServiceHibernateUserImpl(dbConfig);
        prepareDbTestData();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*Context ct = new Context();
        ct.setVariable("name", "foo");
        ct.setVariable("date", LocalDateTime.now().toString());
*/
        Map<String, Object> dataMap = new HashMap<>();
        String userId = req.getParameter("id");
        System.out.println(userId);
        if (userId != null){
        User user = dbUserService.findById(Long.parseLong(userId));
          if (user != null) dataMap.put("user", user);
        }

        System.out.println(templateProcessor.getProcessedTemplate("userAdmin.html", dataMap));
        resp.getWriter().println(templateProcessor.getProcessedTemplate("userAdmin.html", dataMap));

    }


    private void prepareDbTestData() {
        final User user;
        user = new User("Alex", 20);
        user.setAddress(new AddressDataSet("Lomonosova 20"));
        user.setPhoneList(Arrays.asList(new PhoneDataSet("+79031763647"), new PhoneDataSet("+89031763647")));

        dbUserService.create(user);

    }
}
