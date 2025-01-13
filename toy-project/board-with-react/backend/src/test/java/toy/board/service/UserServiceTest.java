package toy.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.user.SignupRequest;
import toy.board.domain.dto.user.UserSimpleInfoResponse;
import toy.board.mapper.UserMapper;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserMapper mapper;
    @Autowired ObjectMapper objectMapper;

    @Transactional
    @Test
    void signupTest() {
        SignupRequest req = new SignupRequest();
        req.setNickname("테스터");
        req.setPassword("1234");
        req.setEmail("tester@test.com");

        UserSimpleInfoResponse res = userService.signup(req);

        System.out.println("res = " + res);
    }

}