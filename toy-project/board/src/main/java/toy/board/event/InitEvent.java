package toy.board.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import toy.board.domain.dto.PostForm;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.Post;
import toy.board.domain.entity.User;
import toy.board.service.PostService;
import toy.board.service.UserService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Configuration
public class InitEvent {

    private final UserService userService;
    private final PostService service;

//    @EventListener(ApplicationStartedEvent.class)
    public void initPosts() {
        UserForm userForm = new UserForm("tester", "1234", "테스터", "tester@tester.com", "테스형");
        User user = userService.save(userForm);

        for (int i = 0; i < 20; i++) {
            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            Post post = new Post("title" + i, "content" + i, now, now, user);
            PostForm form = new PostForm("title" + i, "content" + i, user.getId());
            service.save(form);
        }
    }
}
