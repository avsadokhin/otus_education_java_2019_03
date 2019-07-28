package ru.otus.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final String USER_ADMIN = "useradmin";
    private final String USER_NAME_PARAM = "username";
    private final String USER_PASSWORD_PARAM = "password";
    private final String URL_ADMIN_PATH = "/userAdmin";
    private final int COOKIE_MAX_AGE = 300;

    public LoginServlet() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(USER_NAME_PARAM);
        String password = req.getParameter(USER_PASSWORD_PARAM);

        int cookieMaxAge = COOKIE_MAX_AGE;
        Cookie cookie = new Cookie(USER_NAME_PARAM, username);
        cookie.setComment("user login");
        cookie.setMaxAge(cookieMaxAge);
        resp.addCookie(cookie);
        req.getSession().setMaxInactiveInterval(cookieMaxAge);


        if (USER_ADMIN.equals(username)) resp.sendRedirect(URL_ADMIN_PATH);
        else resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization error. Please try again ");


    }
}
