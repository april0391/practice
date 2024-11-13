package toy.board.domain;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.entity.User;

@SpringBootTest
public class EntityTest {

    @Autowired
    private EntityManager em;

    @Test
    @Commit
    @Transactional
    void test() {
        User user = new User("tester", "1234", "테스형");
        em.persist(user);

        em.flush();
        em.clear();

        System.out.println("user = " + user);

        User user1 = em.find(User.class, user.getId());
        System.out.println("user1 = " + user1);
    }

}
