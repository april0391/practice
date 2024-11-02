package toy.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach() {
        UserForm form = new UserForm();
        form.setUsername("tester");
        form.setRawPassword("1234");
        form.setName("John");
        form.setEmail("tester@tester.com");
        form.setNickname("spring");

    }

    @Test
    void findByIdTest() {
        UserForm userForm = getUserForm();
        User saved = userService.save(userForm);

        User find = userService.findById(saved.getId());

        assertThat(saved).isEqualTo(find);
    }

    @Test
    void authenticateTest() {
        UserForm userForm = getUserForm();
        User saved = userService.save(userForm);

        User authenticated = userService.authenticate("tester", userForm.getRawPassword());

        assertThat(saved).isEqualTo(authenticated);
    }

    UserForm getUserForm() {
        UserForm form = new UserForm();
        form.setUsername("tester");
        form.setRawPassword("1234");
        form.setName("John");
        form.setEmail("tester@tester.com");
        form.setNickname("spring");
        return form;
    }
}