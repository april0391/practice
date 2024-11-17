package toy.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.board.service.HttpSessionSessionManager;
import toy.board.service.JwtSessionManager;
import toy.board.service.SessionManager;

@Configuration
public class SessionConfig {

    @Bean
    public SessionManager sessionManager() {
//        return new HttpSessionSessionManager();
        return new JwtSessionManager();
    }
}
