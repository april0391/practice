package toy.board.repository.jpa;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.entity.Post;

@SpringBootTest
class PostRepositoryJpaTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostRepositoryJpa postRepository;

    @Transactional
    @Test
    void t() {
        String jpql = "SELECT p FROM Post p JOIN FETCH p.user u WHERE p.id = 1";
        Post singleResult = em.createQuery(jpql, Post.class).getSingleResult();
        System.out.println("singleResult = " + singleResult);
    }

}