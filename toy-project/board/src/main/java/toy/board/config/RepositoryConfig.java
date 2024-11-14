package toy.board.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import toy.board.repository.PostRepository;
import toy.board.repository.jpa.PostJpaRepositoryV1;
import toy.board.repository.jpa.PostJpaRepositoryV2;
import toy.board.repository.jpa.PostSpringDataJpaRepository;
import toy.board.repository.mybatis.PostMapper;

@RequiredArgsConstructor
@Configuration
public class RepositoryConfig {

    private final JdbcTemplate template;

    private final PostMapper mapper;

    private final EntityManager em;

    private final PostSpringDataJpaRepository repository;

    @Bean
    public PostRepository postRepository() {
        return new PostJpaRepositoryV1(em);
//        return new PostJpaRepositoryV2(repository);
//        return new PostRepositoryMybatis(mapper);
//        return new PostJdbcTemplateRepositoryV1(template);
    }

}
