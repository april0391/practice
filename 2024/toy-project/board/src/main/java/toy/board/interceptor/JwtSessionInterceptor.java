package toy.board.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import toy.board.domain.entity.User;
import toy.board.service.SessionContextHolder;
import toy.board.util.CookieUtils;
import toy.board.util.JwtUtils;
import toy.board.util.cache.UserCache;

import java.io.IOException;

import static toy.board.util.JwtConst.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtSessionInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    private final UserCache userCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = cookieUtils.getCookieValue(ACCESS_TOKEN_NAME, request);
        if (accessToken != null) {
            // 액세스 토큰 쿠키가 요청에 포함됨
            try {
                // 액세스 토큰 유효성 검증
                jwtUtils.verifyToken(accessToken);

                // 액세스 토큰 유효성 검증 성공
                log.info("access token 유효함");

                // request에 유저 세션 저장
                saveUserToSessionContext(accessToken, request);
                return true;
            } catch (JWTVerificationException e) {
                log.info("access token 유효하지 않음");
                // 액세스 토큰 유효성 검증 실패
                // 1. 잘못된 토큰 값일 경우 : 액세스 토큰 쿠키 삭제
                // 2. 토큰이 만료된 경우 : 리프래시 토큰 사용해서 액세스 토큰 재생성 후 리디렉션
                if (e instanceof TokenExpiredException) {
                    // 리프래시 토큰 쿠키 확인
                    checkRefreshToken(request, response);
                }
            }
        }
        // 액세스 토큰 쿠키가 요청에 포함되지 않음
        log.info("access token 없음");
        // 리프래시 토큰 쿠키 확인
        checkRefreshToken(request, response);
        return true;
    }

    private void checkRefreshToken(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("refresh token 확인합니다");
        String refreshToken = cookieUtils.getCookieValue(REFRESH_TOKEN_NAME, request);
        if (refreshToken != null) {
            log.info("refresh token 확인됨");
            // 리프래시 토큰 쿠키가 요청에 포함됨
            try {
                // 리프래시 토큰 유효성 검증
                jwtUtils.verifyToken(refreshToken);

                // 리프래시 토큰 유효성 검증 성공
                log.info("refresh token 검증 성공");

                // 액세스 토큰 재발급
                String subject = jwtUtils.getSubject(refreshToken);
                String newAccessToken = jwtUtils.createToken(subject, EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN);
                cookieUtils.createCookieAndAddToResponse(ACCESS_TOKEN_NAME, newAccessToken, EXPIRATION_TIME_SECOND_OF_ACCESS_TOKEN, response);

                // request에 유저 세션 저장
                saveUserToSessionContext(refreshToken, request);
                return;

            } catch (JWTVerificationException e) {
                log.info("refresh token 유효하지 않음");
                // 리프래시 토큰 유효성 검증 실패
                // 1. 잘못된 토큰 값일 경우 : 리프래시 토큰 쿠키 삭제

                // 2. 토큰이 만료된 경우 : ? 어떻게 할 것인가?
            }
        }
        log.info("refresh token 없음");
    }

    private void saveUserToSessionContext(String token, HttpServletRequest request) {
        String subject = jwtUtils.getSubject(token);
        User user = userCache.getUser(Long.valueOf(subject));
        SessionContextHolder.setSession(user);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionContextHolder.clearSession();
    }
}
