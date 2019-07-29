package ru.otus.spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dbservice.DbService;
import ru.otus.entity.PhoneDataSet;
import ru.otus.entity.User;
import ru.otus.spring.cookie.CookieControl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class UserController {
    private final int COOKIE_MAX_AGE = 300;
    private final String USER_ADMIN = "useradmin";

    private final String WEB_PARAM_USERNAME = "username";
    private final String WEB_PARAM_USER_ID = "id";

    private final String MODEL_ATTR_USERNAME = "username";
    private final String MODEL_ATTR_DATE = "date";
    private final String MODEL_ATTR_USER = "user";
    private final String MODEL_ATTR_USER_ID = "id";

    private final DbService<User> repository;

    public UserController(DbService<User> repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String userLogin(Model model) {
        return "login.html";
    }

    @PostMapping("/login")
    public RedirectView userAdmin(@RequestParam(name = WEB_PARAM_USERNAME) String name
            , HttpServletResponse resp) {

        int cookieMaxAge = COOKIE_MAX_AGE;
        Cookie cookie = new Cookie(CookieControl.COOKIE_KEY_USERNAME, name);
        cookie.setComment("user login");
        cookie.setMaxAge(cookieMaxAge);
        resp.addCookie(cookie);

        RedirectView redirectView = new RedirectView("userAdmin", true);
        if (USER_ADMIN.equals(name)) return redirectView;
        else redirectView.setStatusCode(HttpStatus.UNAUTHORIZED);

        return redirectView;

    }

    @GetMapping("/userAdmin")
    public String userAdmin(Model model
            , @RequestParam(name = WEB_PARAM_USER_ID, required = false) String id
            , HttpServletRequest req) {

        Map<String, Optional<Cookie>> cookieMap = new HashMap<>();
        cookieMap = CookieControl.getCookieMap(req);
        cookieMap.get(CookieControl.COOKIE_KEY_USERNAME).ifPresent(cookie -> model.addAttribute(MODEL_ATTR_USERNAME, cookie.getValue()));

        model.addAttribute(MODEL_ATTR_DATE, LocalDateTime.now().toString());
        model.addAttribute(MODEL_ATTR_USER_ID, id);

        if (id != null) {
            User user = repository.findById(Long.parseLong(id));
            if (user != null) model.addAttribute(MODEL_ATTR_USER, user);
        }
        return "userAdmin.html";
    }

    @GetMapping("/userAdminCreate")
    public String userCreate(Model model) {
        model.addAttribute(MODEL_ATTR_USER, new User());
        model.addAttribute("phone", Arrays.asList(new PhoneDataSet()));
        return "userAdminCreate.html";
    }

    @PostMapping("/userAdminCreate/save")
    public RedirectView userSave(@ModelAttribute User user, HttpServletRequest request, RedirectAttributes attributes) {

        RedirectView redirectView = new RedirectView("/userAdmin", true);

        final String[] phoneNumbers = request.getParameterValues("number");


        List<PhoneDataSet> phoneDataSetList = new ArrayList<>();
        for (String phone : phoneNumbers) {
            if (!phone.isBlank()) {
                phoneDataSetList.add(new PhoneDataSet(phone));
            }

        }

        if (user != null) {
           user.setPhoneList(phoneDataSetList);
            repository.create(user);
            attributes.addAttribute(WEB_PARAM_USER_ID, String.valueOf(user.getId()));
        } else {
            redirectView.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
        }

        return redirectView;
    }

}
