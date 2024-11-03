package toy.board.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import toy.board.repository.UserRepository;
import toy.board.repository.jdbc.JdbcTemplateUserRepository;
import toy.board.repository.jpa.IntegratedJpaUserRepository;
import toy.board.repository.jpa.JpaUserRepository;
import toy.board.repository.jpa.SpringDataJpaUserRepository;
import toy.board.repository.mybatis.MybatisUserRepository;
import toy.board.repository.mybatis.UserMapper;
import toy.board.security.BcryptPasswordEncoder;
import toy.board.security.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class AppConfig {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate parameterJdbcTemplate;

    private final UserMapper mapper;

    private final EntityManager em;

    private final SpringDataJpaUserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BcryptPasswordEncoder();
    }

    @Bean
    public UserRepository userRepository() {
//        return new JpaUserRepository(em);
//        return new JdbcTemplateUserRepository(jdbcTemplate, parameterJdbcTemplate);
//        return new MybatisUserRepository(mapper);
        return new IntegratedJpaUserRepository(userRepository);
    }

}
