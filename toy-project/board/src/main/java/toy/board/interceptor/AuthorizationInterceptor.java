package toy.board.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import toy.board.service.SessionManager;

@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final SessionManager sessionManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object sessionData = sessionManager.getSession(request, response);
        if (sessionData == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect("/signin");
            return false;
        }
        return true;
    }

}
