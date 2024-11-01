package toy.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import toy.board.repository.PostRepository;

@RequiredArgsConstructor
@Configuration
public class InitConfig {

    private final PostRepository postRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void initPosts() {
        System.out.println("InitConfig.initPosts");
    }
}
