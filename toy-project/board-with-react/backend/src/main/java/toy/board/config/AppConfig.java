package toy.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import toy.board.domain.entity.PostEntity;
import toy.board.repository.PostRepository;

@RequiredArgsConstructor
@Configuration
public class AppConfig {

    private final PostRepository postRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initPostData() {
        for (int i = 1; i <= 100; i++) {
            PostEntity entity = new PostEntity();
            entity.setTitle("제목" + i);
            entity.setContent("내용" + i);
            postRepository.save(entity);
        }
    }
}
