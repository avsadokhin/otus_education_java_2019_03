package ru.otus.web.servlets;

import ru.otus.dbservice.DbService;
import ru.otus.entity.AddressDataSet;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;
import ru.otus.web.TemplateProcessor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class UserAdminCreateServlet extends HttpServlet {
    private static String CONTENT_TYPE = "text/html; charset=utf-8";
    private final String URL_ADMIN_PATH = "userAdmin";
    private final TemplateProcessor templateProcessor;
    private DbService<User> dbUserService;

    public UserAdminCreateServlet(DbService<User> dbUserService, TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
        this.dbUserService = dbUserService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> dataMap = new HashMap<>();

        System.out.println(templateProcessor.getProcessedTemplate("userAdminCreate.html", dataMap));
        resp.getWriter().println(templateProcessor.getProcessedTemplate("userAdminCreate.html", dataMap));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        user = getUserDataFromRequest(req);

        if (user != null) {
            dbUserService.create(user);
            req.setAttribute("_id", String.valueOf(user.getId()));
            resp.setContentType(CONTENT_TYPE);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setContentType(CONTENT_TYPE);
            resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
        req.getRequestDispatcher(URL_ADMIN_PATH).forward(req, resp);

    }

    private User getUserDataFromRequest(HttpServletRequest request) {
        final String username = request.getParameter("name");
        final String ageString = request.getParameter("age");
        final String address = request.getParameter("address");
        final String[] phoneNumbers = request.getParameterValues("number");

        int age = 0;
        if (ageString != null && !ageString.isBlank()) {
            age = Integer.parseInt(ageString);
        }

        List<PhoneDataSet> phoneDataSetList = new ArrayList<>();
        for (String phone : phoneNumbers) {
            if (!phone.isBlank()) {
                phoneDataSetList.add(new PhoneDataSet(phone));
            }

        }

        final User user = new User(username, age);
        user.setAddress(new AddressDataSet(address));
        user.setPhoneList(phoneDataSetList);

        return user;
    }


}
