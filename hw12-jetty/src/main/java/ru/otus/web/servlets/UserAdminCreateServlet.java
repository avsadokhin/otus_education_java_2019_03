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
    public static String pathSpec = "/userAdminCreate";
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
        user.setName(req.getParameter("name"));
        user.setAge(Integer.parseInt(req.getParameter("age")));
        user.setAddress(new AddressDataSet(req.getParameter("address")));

        String[] phoneNumberList = req.getParameterValues("number");
        List<PhoneDataSet> phoneDataSetList = new ArrayList<>();
        for (String phone : phoneNumberList) {
            if (!phone.isBlank()) {
                phoneDataSetList.add(new PhoneDataSet(phone));
            }

        }
        user.setPhoneList(phoneDataSetList);

        dbUserService.create(user);

        req.setAttribute("_id", String.valueOf(user.getId()));

        req.getRequestDispatcher("userAdmin").forward(req, resp);

    }


}
