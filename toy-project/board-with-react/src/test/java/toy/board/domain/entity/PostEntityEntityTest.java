package toy.board.domain.entity;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;

import javax.sql.DataSource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
public class PostEntityTest {

    @Autowired
    EntityManager em;

    @Commit
    @Test
    void entityTest() {
        User user = new User();
        user.setEmail("tester@test.com");
        user.setPassword("1234");
        em.persist(user);

        Post post1 = new Post();
        post1.setTitle("title1");
        post1.setContent("content1");
        post1.setUser(user);
        em.persist(post1);
        em.flush();
        em.clear();

        User findUser = em.find(User.class, user.getId());
        System.out.println("findUser = " + findUser);
    }


}
