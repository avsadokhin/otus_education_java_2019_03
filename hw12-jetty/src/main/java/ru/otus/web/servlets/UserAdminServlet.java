package ru.otus.web.servlets;

import ru.otus.dbservice.DbService;
import ru.otus.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ru.otus.web.TemplateProcessor;

import java.time.LocalDateTime;
import java.util.*;

public class UserAdminServlet extends HttpServlet {
    public static String pathSpec = "/userAdmin";
    private final TemplateProcessor templateProcessor;
    private DbService<User> dbUserService;

    public UserAdminServlet(DbService<User> dbUserService, TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
        this.dbUserService = dbUserService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> dataMap = new HashMap<>();

        String userName = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie
                        .getName()
                        .equals("username"))
                .findFirst()
                .get()
                .getValue();
        req.getServletContext().setAttribute("username", userName);
        req.getServletContext().setAttribute("date", LocalDateTime.now().toString());
        dataMap.put("username", userName);
        dataMap.put("date", LocalDateTime.now().toString());

        String userId;
        if (req.getAttribute("_id") != null)
            userId = req.getHeader("_id");
        else
            userId = req.getParameter("id");


        dataMap.put("id", userId);

        System.out.println("Userid = " + userId);
        if (userId != null) {
            User user = dbUserService.findById(Long.parseLong(userId));
            if (user != null) dataMap.put("user", user);

        }

        resp.getWriter().println(templateProcessor.getProcessedTemplate("userAdmin.html", dataMap));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("username", req.getServletContext().getAttribute("username"));
        dataMap.put("date", req.getServletContext().getAttribute("date"));


        String userId;

        userId = String.valueOf(req.getAttribute("_id"));


        System.out.println("Userid = " + userId);
        if (userId != null) {
            dataMap.put("id", userId);
            User user = dbUserService.findById(Long.parseLong(userId));
            if (user != null) dataMap.put("user", user);

        }

        resp.getWriter().println(templateProcessor.getProcessedTemplate("userAdmin.html", dataMap));

    }
}
