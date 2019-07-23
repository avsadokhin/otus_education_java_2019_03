package ru.otus.web.Cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CookieControl {
    public static Map<String, Optional<Cookie>> getCookieMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, Optional<Cookie>> ret = new HashMap<>();

        Arrays.stream(cookies).forEach(cookie -> ret.put(cookie.getName(), Optional.of(cookie)));

        return ret;
    }
}
