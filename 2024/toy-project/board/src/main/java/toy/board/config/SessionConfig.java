package toy.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.board.repository.UserRepository;
import toy.board.repository.jpa.UserSessionSpringJpaRepository;
import toy.board.service.JwtSessionManager;
import toy.board.service.SessionManager;
import toy.board.util.*;
import toy.board.util.cache.DbUserCache;
import toy.board.util.cache.NoCacheUserCache;
import toy.board.util.cache.UserCache;

@RequiredArgsConstructor
@Configuration
public class SessionConfig {

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    private final UserRepository userRepository;
    private final UserSessionSpringJpaRepository userSessionSpringJpaRepository;

    @Bean
    public SessionManager sessionManager() {
//        return new HttpSessionSessionManager();
        return new JwtSessionManager(jwtUtils, cookieUtils);
    }

    @Bean
    public UserCache userCache() {
        return new NoCacheUserCache(userRepository);
//        return new DbUserCache(userSessionSpringJpaRepository);
    }

}
