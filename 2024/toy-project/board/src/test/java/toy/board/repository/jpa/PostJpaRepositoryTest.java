package toy.board.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.entity.Post;
import toy.board.domain.entity.User;
import toy.board.repository.UserRepository;

@SpringBootTest
class PostJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostJpaRepositoryV1 postRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Test
    void find() {
        User user = new User("tester", "1234", "테스터");
        em.persist(user);
        Post post = new Post("제목", "내용", user);
        em.persist(post);
        em.flush();
        em.clear();

        Post post1 = em.find(Post.class, post.getId());
        System.out.println("post1 = " + post1);
    }

}