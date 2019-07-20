package ru.otus.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    public LoginServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        int cookieMaxAge = 300;
        Cookie cookie = new Cookie("username", username);
        cookie.setComment("user login");
        cookie.setMaxAge(cookieMaxAge);
        resp.addCookie(cookie);
        req.getSession().setMaxInactiveInterval(cookieMaxAge);

        if ("useradmin".equals(username)) resp.sendRedirect("/userAdmin");
        else resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization error. Please try again ");


    }
}
