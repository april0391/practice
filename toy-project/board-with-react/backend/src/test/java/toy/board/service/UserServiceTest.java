package toy.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.UserSignupRequest;
import toy.board.domain.dto.UserSignupResponse;
import toy.board.domain.entity.UserEntity;
import toy.board.mapper.UserMapper;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserMapper mapper;
    @Autowired ObjectMapper objectMapper;

    @Transactional
    @Test
    void signupTest() {
        UserSignupRequest req = new UserSignupRequest();
        req.setNickname("테스터");
        req.setInputPassword("1234");
        req.setEmail("tester@test.com");

        UserSignupResponse res = userService.signup(req);

        System.out.println("res = " + res);

        UserEntity byId = userService.findById(res.getId());

        UserSignupResponse userSignupResponse = mapper.entityToSignupResponse(byId);

        System.out.println("userSignupResponse = " + userSignupResponse);
    }

}