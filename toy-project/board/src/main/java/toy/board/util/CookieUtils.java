package toy.board.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtils {

    public void createAndAddCookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = createCookie(name, value, maxAge);
        response.addCookie(cookie);
    }

    public String getCookieValue(String name, HttpServletRequest request) {
        Cookie cookie = getCookie(name, request);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    public void invalidateCookie(String name, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getCookie(name, request);
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    private Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    private void addCookie(Cookie cookie, HttpServletResponse response) {
        response.addCookie(cookie);
    }

    private Cookie getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
