package edu.jwt;

import edu.jwt.model.User;
import edu.jwt.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(UserRepository userRepository) {
		return args -> {
			userRepository.save(new User("user", "1234", List.of("ROLE_USER")));
			userRepository.save(new User("manager", "1234", List.of("ROLE_MANAGER")));
			userRepository.save(new User("admin", "1234", List.of("ROLE_ADMIN")));
		};
	}

}
