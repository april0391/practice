package toy.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import toy.board.interceptor.AuthorizationInterceptor;
import toy.board.interceptor.JwtSessionInterceptor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtSessionInterceptor jwtSessionInterceptor;
    private final AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtSessionInterceptor)
                .addPathPatterns("/**")
                .order(1);

        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/posts/**")
                .excludePathPatterns("/posts", "/posts/{id:[0-9]+}")
                .order(2);
    }
}
