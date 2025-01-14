package toy.board.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import toy.board.exception.AuthException;
import toy.board.util.JwtUtils;

import static toy.board.exception.AuthException.ErrorCode.*;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new AuthException(AUTHORIZATION_HEADER_NOT_FOUND);
        }
        if (!token.startsWith("Bearer ")) {
            throw new AuthException(INVALID_TOKEN_FORMAT);
        }
        jwtUtils.verifyToken(token.substring(7));
        return true;
    }

}
