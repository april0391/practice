package toy.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.board.service.HttpSessionSessionManager;
import toy.board.service.JwtSessionManager;
import toy.board.service.SessionManager;
import toy.board.util.CookieUtils;

@RequiredArgsConstructor
@Configuration
public class SessionConfig {

    private final CookieUtils cookieUtils;

    @Bean
    public SessionManager sessionManager() {
//        return new HttpSessionSessionManager();
        return new JwtSessionManager(cookieUtils);
    }
}
