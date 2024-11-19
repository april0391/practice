package toy.board.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import toy.board.domain.entity.User;
import toy.board.util.CookieUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class JwtSessionManager implements SessionManager {

    private final CookieUtils cookieUtils;

    private final String SECRET_KEY = "test-secret-key";
    private final String ISSUER = "toy-board";
    private final String ACCESS_TOKEN_NAME = "access";
    private final String REFRESH_TOKEN_NAME = "refresh";

    private final int EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN = 60;
    private final int EXPIRATION_TIME_SECOND_OF_REFRESH_TOKEN = 300;

    private final Map<Long, String> refreshTokenStore = new ConcurrentHashMap<>();

    public static final Map<Long, User> userCache = new ConcurrentHashMap<>();

    private final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
            .withIssuer(ISSUER)
            .build();

    @Override
    public void createSession(User user, HttpServletRequest request, HttpServletResponse response) {
        Long id = user.getId();
        String access = createToken(String.valueOf(id), EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN);
        String refresh = createToken(String.valueOf(id), EXPIRATION_TIME_SECOND_OF_REFRESH_TOKEN);

        cookieUtils.createAndAddCookie(ACCESS_TOKEN_NAME, access, EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN, response);
        cookieUtils.createAndAddCookie(REFRESH_TOKEN_NAME, refresh, EXPIRATION_TIME_SECOND_OF_REFRESH_TOKEN, response);

        refreshTokenStore.put(id, refresh);

        userCache.put(id, user);
    }

    private String createToken(String subject, int expirationTimeSecond) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeSecond * 1000L);
        return JWT.create()
                .withIssuer(ISSUER) // 발급자
                .withSubject(subject) // 사용자 정보
                .withIssuedAt(now) // 발급 시간
                .withExpiresAt(expiryDate) // 만료 시간
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 서명 알고리즘 및 키
    }

    @Override
    public Object getSessionData(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = cookieUtils.getCookieValue(ACCESS_TOKEN_NAME, request);
        if (isTokenValid(accessToken)) {
            log.info("access token 유효함.");
            return getUserFromToken(accessToken);
        }

        log.info("access token 유효하지 않음. refresh token 확인합니다.");
        String refreshToken = cookieUtils.getCookieValue(REFRESH_TOKEN_NAME, request);
        if (isTokenValid(refreshToken)) {
            log.info("refresh token 유효함.");
            User user = getUserFromToken(refreshToken);
            refreshAccessToken(String.valueOf(user.getId()), response);
            return user;
        }
        return null;
    }

    private boolean isTokenValid(String token) {
        if (token == null) {
            return false;
        }
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    private User getUserFromToken(String token) {
        DecodedJWT verify = verifier.verify(token);
        String subject = verify.getSubject();
        return userCache.get(Long.valueOf(subject));
    }

    private void refreshAccessToken(String subject, HttpServletResponse response) {
        log.info("access token 갱신합니다.");
        String newAccessToken = createToken(subject, EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN);
        cookieUtils.createAndAddCookie(ACCESS_TOKEN_NAME, newAccessToken, EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN, response);
    }

    @Override
    public void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
        String accessJwt = cookieUtils.getCookieValue(ACCESS_TOKEN_NAME, request);

        DecodedJWT jwt = verifier.verify(accessJwt);
        String subject = jwt.getSubject();
        userCache.remove(Long.valueOf(subject));

        cookieUtils.invalidateCookie(ACCESS_TOKEN_NAME, request, response);
        cookieUtils.invalidateCookie(REFRESH_TOKEN_NAME, request, response);
    }

}
