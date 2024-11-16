package toy.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.Post;
import toy.board.domain.entity.User;
import toy.board.service.PostService;
import toy.board.service.UserService;

@RequiredArgsConstructor
@Configuration
public class InitEvent {

    private final ApplicationContext ac;
    private final UserService userService;
    private final PostService postService;

//    @Bean
    public ApplicationRunner applicationRunner() {
        UserForm userForm = new UserForm("tester", "1234", "테스형");
        User savedUser = userService.save(userForm);
        return args -> {
            for (int i = 0; i < 100; i++) {
                Post post = new Post("제목" + i, "내용" + i, savedUser);
                postService.save(post);
            }
        };
    }
}
