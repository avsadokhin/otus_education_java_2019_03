package ru.otus.web.servlets;

import ru.otus.dbservice.DbService;
import ru.otus.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ru.otus.web.Cookie.CookieControl;
import ru.otus.web.TemplateProcessor;

import java.time.LocalDateTime;
import java.util.*;

public class UserAdminServlet extends HttpServlet {
    private final TemplateProcessor templateProcessor;
    private DbService<User> dbUserService;

    public UserAdminServlet(DbService<User> dbUserService, TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
        this.dbUserService = dbUserService;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> dataMap = new HashMap<>();

        Map<String, Optional<Cookie>> cookieMap = new HashMap<>();
        cookieMap = CookieControl.getCookieMap(req);


        cookieMap.get("username").ifPresent(cookie -> dataMap.put("username", cookie.getName()));
        dataMap.put("date", LocalDateTime.now().toString());

        String userId;
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
        Map<String, Optional<Cookie>> cookieMap = new HashMap<>();
        cookieMap = CookieControl.getCookieMap(req);


        cookieMap.get("username").ifPresent(cookie -> dataMap.put("username", cookie.getName()));
        dataMap.put("date", LocalDateTime.now().toString());


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
