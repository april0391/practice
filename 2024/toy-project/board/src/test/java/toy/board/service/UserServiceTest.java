package toy.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;
import toy.board.exception.UserException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    User saved;

    private final String USERNAME = "tester";
    private final String PASSWORD = "1234";

    @BeforeEach
    void beforeEach() {
        UserForm userForm = new UserForm(USERNAME, PASSWORD, "테스형");
        saved = userService.save(userForm);
    }

    @Test
    void saveAndFindById() {
        User find = userService.findById(saved.getId()).orElseThrow();
        assertThat(saved).isEqualTo(find);
    }

    @Test
    void loginSuccess() {
        User user = userService.signin(USERNAME, PASSWORD);
        assertThat(saved).isEqualTo(user);
    }
    
    @Test
    void usernameInvalid() {
        assertThatThrownBy(() -> userService.signin("x", PASSWORD))
                .isInstanceOf(UserException.class)
                .extracting("errorCode")  // UserException 클래스의 필드명인 errorCode를 추출
                .isEqualTo(UserException.ErrorCode.USERNAME_INVALID);
    }

    @Test
    void passwordIncorrect() {
        assertThatThrownBy(() -> userService.signin(USERNAME, "x"))
                .isInstanceOf(UserException.class)
                .extracting("errorCode")
                .isEqualTo(UserException.ErrorCode.PASSWORD_INCORRECT);
    }

}