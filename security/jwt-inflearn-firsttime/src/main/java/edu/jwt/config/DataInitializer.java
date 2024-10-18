package edu.jwt.config;

import edu.jwt.model.User;
import edu.jwt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.save(new User("ssar", passwordEncoder.encode("1234"), "ROLE_USER"));
            userRepository.save(new User("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN"));
        };
    }
}
