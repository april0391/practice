package toy.board.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import toy.board.domain.entity.User;
import toy.board.util.CookieUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtSessionManagerTest {

    private JwtSessionManager sessionManager;

    @Mock
    private CookieUtils cookieUtils;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private User user = new User(1L, "tester", "1234", "테스터");

    @BeforeEach
    void setUp() {
        sessionManager = new JwtSessionManager(cookieUtils);
    }

    @Test
    void testCreateSession_withJwtValidation() {
        sessionManager.createSession(user, request, response);

        // 1. 캐시에서 유저 검증
        User findUserFromCache = JwtSessionManager.userCache.get(user.getId());
        assertThat(user).isEqualTo(findUserFromCache);

        // 2. ArgumentCaptor로 JWT 토큰 캡처
        ArgumentCaptor<String> accessTokenCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> refreshTokenCaptor = ArgumentCaptor.forClass(String.class);

        verify(cookieUtils, times(1)).createAndAddCookie(eq("access"), accessTokenCaptor.capture(), eq(60), eq(response));
        verify(cookieUtils, times(1)).createAndAddCookie(eq("refresh"), refreshTokenCaptor.capture(), eq(300), eq(response));

        // 3. 캡처된 JWT 디코딩
        String capturedAccessToken = accessTokenCaptor.getValue();
        String capturedRefreshToken = refreshTokenCaptor.getValue();

        Algorithm algorithm = Algorithm.HMAC256("test-secret-key");
        DecodedJWT decodedAccessJwt = JWT.require(algorithm).withIssuer("toy-board").build().verify(capturedAccessToken);
        DecodedJWT decodedRefreshJwt = JWT.require(algorithm).withIssuer("toy-board").build().verify(capturedRefreshToken);

        // 4. JWT 내용 검증
        assertThat(decodedAccessJwt.getSubject()).isEqualTo(String.valueOf(user.getId()));
        assertThat(decodedRefreshJwt.getSubject()).isEqualTo(String.valueOf(user.getId()));

        // 유효시간 등 추가 검증 가능
        assertThat(decodedAccessJwt.getExpiresAt()).isNotNull();
        assertThat(decodedRefreshJwt.getExpiresAt()).isNotNull();
    }



}
