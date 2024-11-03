package toy.board.app;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    ApplicationContext ac;

    @Test
    void bean() {
        JdbcTemplate jdbcTemplate = ac.getBean(JdbcTemplate.class);
        System.out.println("jdbcTemplate = " + jdbcTemplate.getClass());

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = ac.getBean(NamedParameterJdbcTemplate.class);
        System.out.println("namedParameterJdbcTemplate = " + namedParameterJdbcTemplate.getClass());

        SimpleJdbcInsert simpleJdbcInsert = ac.getBean(SimpleJdbcInsert.class);
        System.out.println("simpleJdbcInsert = " + simpleJdbcInsert.getClass());

        EntityManager em = ac.getBean(EntityManager.class);
        System.out.println("em.getClass() = " + em.getClass());
    }
}
