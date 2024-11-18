package toy.board.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import toy.board.domain.entity.User;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JwtSessionManager implements SessionManager {

    private final String SECRET_KEY = "test-secret-key";
    private final String ISSUER = "toy-board";
    private final String ACCESS_TOKEN_NAME = "access";
    private final String REFRESH_TOKEN_NAME = "refresh";

    private static final Map<Long, User> userCache = new ConcurrentHashMap<>();

    @Override
    public void createSession(User user, Object sessionStore) {
        int accessExpirationTimeSecond = 60;
        String access = createJwt(user, accessExpirationTimeSecond);
        Cookie accessCookie = createCookie(ACCESS_TOKEN_NAME, access, accessExpirationTimeSecond);

        int refreshExpirationTimeSecond = 5 * 60;
        String refresh = createJwt(user, refreshExpirationTimeSecond);
        Cookie refreshCookie = createCookie(REFRESH_TOKEN_NAME, refresh, refreshExpirationTimeSecond);

        HttpServletResponse response = (HttpServletResponse) sessionStore;
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);

        userCache.put(user.getId(), user);
    }

    private Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    private String createJwt(User user, int expirationTimeSecond) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeSecond * 1000L);
        return JWT.create()
                .withIssuer(ISSUER) // 발급자
                .withSubject(String.valueOf(user.getId())) // 사용자 정보
                .withIssuedAt(now) // 발급 시간
                .withExpiresAt(expiryDate) // 만료 시간
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 서명 알고리즘 및 키
    }

    @Override
    public Object getSessionData(HttpServletRequest request, HttpServletResponse response) {
        Cookie accessCookie = getCookie(ACCESS_TOKEN_NAME, request);
        Cookie refreshCookie = getCookie(REFRESH_TOKEN_NAME, request);
        if (accessCookie == null && refreshCookie == null) {
            return null;
        }
        try {
            DecodedJWT jwt = getDecodedJWT(accessCookie);
            String subject = jwt.getSubject();

            return userCache.get(Long.valueOf(subject));

        } catch (JWTVerificationException e) {
            if (e instanceof TokenExpiredException) {
                DecodedJWT decodedJWT = getDecodedJWT(refreshCookie);
                String subject = decodedJWT.getSubject();
                User user = userCache.get(Long.valueOf(subject));
                String jwt = createJwt(user, 60);
                Cookie cookie = createCookie(ACCESS_TOKEN_NAME, jwt, 60);
                response.addCookie(cookie);
                return user;
            }
            System.err.println("Invalid token: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getCookie(ACCESS_TOKEN_NAME, request);
        if (cookie == null) {
            return;
        }
        DecodedJWT jwt = getDecodedJWT(cookie);
        String subject = jwt.getSubject();
        userCache.remove(Long.valueOf(subject));

        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private DecodedJWT getDecodedJWT(Cookie cookie) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        // JWT 검증기 생성
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER) // 발급자 확인
                .build();

        // 토큰 검증 및 디코딩
        DecodedJWT verify = verifier.verify(cookie.getValue());
        return verify;
    }

    private Cookie getCookie(String cookieName, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .orElse(null);
    }

}
