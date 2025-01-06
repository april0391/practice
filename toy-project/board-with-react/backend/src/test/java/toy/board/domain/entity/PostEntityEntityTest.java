package toy.board.domain.entity;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
public class PostEntityEntityTest {

    @Autowired
    EntityManager em;

    @Commit
    @Test
    void entityTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("tester@test.com");
        userEntity.setPassword("1234");
        em.persist(userEntity);

        PostEntity postEntity1 = new PostEntity();
        postEntity1.setTitle("title1");
        postEntity1.setContent("content1");
        postEntity1.setUser(userEntity);
        em.persist(postEntity1);
        em.flush();
        em.clear();

        UserEntity findUserEntity = em.find(UserEntity.class, userEntity.getId());
        System.out.println("findUser = " + findUserEntity);
    }


}
