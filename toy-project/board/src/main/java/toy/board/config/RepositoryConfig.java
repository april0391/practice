package toy.board.config;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.board.repository.PostRepository;
import toy.board.repository.jpa.PostRepositoryJpa;
import toy.board.repository.mybatis.PostMapper;
import toy.board.repository.mybatis.PostRepositoryMybatis;

@RequiredArgsConstructor
@Configuration
public class RepositoryConfig {

    private final PostMapper mapper;
    private final EntityManager em;

    @Bean
    public PostRepository postRepository() {
//        return new PostRepositoryJpa(em);
        return new PostRepositoryMybatis(mapper);
    }

}
