package toy.board.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
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
    private final String JWT_COOKIE_NAME = "authorization";

    private final Map<Long, User> userCache = new ConcurrentHashMap<>();

    @Override
    public void createSession(User user, Object sessionStore) {
        long expirationTime = 60 * 1000;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        String jwt = JWT.create()
                .withIssuer(ISSUER) // 발급자
                .withSubject(String.valueOf(user.getId())) // 사용자 정보
                .withIssuedAt(now) // 발급 시간
                .withExpiresAt(expiryDate) // 만료 시간
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 서명 알고리즘 및 키

        Cookie cookie = new Cookie(JWT_COOKIE_NAME, jwt);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60);

        HttpServletResponse response = (HttpServletResponse) sessionStore;
        response.addCookie(cookie);

        userCache.put(user.getId(), user);
    }

    @Override
    public Object getSessionData(Object sessionStore) {
        HttpServletRequest request = (HttpServletRequest) sessionStore;
        Cookie cookie = getCookie(JWT_COOKIE_NAME, request);
        if (cookie == null) {
            return null;
        }
        try {
            DecodedJWT jwt = getDecodedJWT(cookie);

            // 필요한 클레임 확인
            String subject = jwt.getSubject();
            System.out.println("Valid token for subject: " + subject);

            return userCache.get(Long.valueOf(subject));

        } catch (JWTVerificationException e) {
            System.err.println("Invalid token: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = getCookie(JWT_COOKIE_NAME, request);
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
