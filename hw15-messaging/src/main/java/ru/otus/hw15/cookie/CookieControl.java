package ru.otus.hw15.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CookieControl {
    public final static String COOKIE_KEY_USERNAME = "username";
    public final static String COOKIE_KEY_USERPASS = "userpass";

    public static Map<String, Optional<Cookie>> getCookieMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, Optional<Cookie>> ret = new HashMap<>();

        Arrays.stream(cookies).forEach(cookie -> ret.put(cookie.getName(), Optional.of(cookie)));

        return ret;
    }
}
