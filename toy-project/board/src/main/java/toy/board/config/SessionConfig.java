package toy.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.board.repository.UserRepository;
import toy.board.service.HttpSessionSessionManager;
import toy.board.service.JwtSessionManager;
import toy.board.service.SessionManager;
import toy.board.util.CookieUtils;
import toy.board.util.JwtUtils;
import toy.board.util.NoCacheUserCache;
import toy.board.util.UserCache;

@RequiredArgsConstructor
@Configuration
public class SessionConfig {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    private final UserRepository userRepository;

    @Bean
    public SessionManager sessionManager() {
//        return new HttpSessionSessionManager();
        return new JwtSessionManager(jwtUtils, cookieUtils, userCache());
    }

    @Bean
    public UserCache userCache() {
        return new NoCacheUserCache(userRepository);
    }

}
