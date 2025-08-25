package toy.board.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.board.domain.dto.user.SignupRequest;
import toy.board.mapper.UserMapper;
import toy.board.repository.UserRepository;

@SpringBootTest
public class UserEntityTest {

    @Autowired
    UserRepository repository;

    @Autowired
    UserMapper mapper;

    @Test
    void test() {
        SignupRequest req = new SignupRequest();
        req.setNickname("테스터");
        req.setPassword("1234");
        req.setEmail("tester@test.com");

    }
}
