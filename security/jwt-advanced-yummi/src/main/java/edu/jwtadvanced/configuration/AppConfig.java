package edu.jwtadvanced.configuration;

import edu.jwtadvanced.entity.User;
import edu.jwtadvanced.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class AppConfig {

    private final UserService userService;

//    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            User user = new User("yummi", "1234", "ROLE_USER");
            User saved = userService.save(user);
            System.out.println("saved = " + saved);
        };
    }
}
