package toy.board.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;
import toy.board.exception.UserException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    PlatformTransactionManager txManager;
    @Autowired
    EntityManager em;

    User saved;

    @BeforeEach
    void beforeEach() {
        UserForm form = new UserForm();
        form.setUsername("tester");
        form.setRawPassword("1234");
        form.setName("John");
        form.setEmail("tester@tester.com");
        form.setNickname("spring");
        saved = userService.save(form);
    }

    @AfterEach
    void afterEach() {
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionDefinition());
        em.createNativeQuery("DELETE FROM users").executeUpdate();
        txManager.commit(status);
    }

    @Test
    void findById() {
        User find = userService.findById(saved.getId());

        assertThat(saved).isEqualTo(find);
    }

    @Test
    void authenticate_success() {
        User authenticated = userService.authenticate("tester", "1234");

        assertThat(saved).isEqualTo(authenticated);
    }

    @Test
    void authenticate_invalidUsername() {
        assertThatThrownBy(() -> userService.authenticate("xxx", "1234"))
                .isInstanceOf(UserException.class)
                .extracting("errorCode")
                .isEqualTo(UserException.ErrorCode.INVALID_USERNAME);
    }

    @Test
    void authenticate_invalidPassword() {
        assertThatThrownBy(() -> userService.authenticate("tester", "xxx"))
                .isInstanceOf(UserException.class)
                .extracting("errorCode")
                .isEqualTo(UserException.ErrorCode.INVALID_PASSWORD);
    }

}