package toy.board.repository.jdbc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.entity.Post;
import toy.board.repository.PostRepository;

@SpringBootTest
class PostRepositoryJdbcTemplateTest {

    @Autowired
    PostRepository postRepository;

    @Transactional
    @Test
    void save() {
        System.out.println(postRepository.getClass());

        Post post = new Post();
        post.setTitle("제목");
        post.setContent("내용");
        post.setUserId(1L);
        postRepository.save(post);

        System.out.println("post = " + post);

        Post find = postRepository.findById(post.getId()).get();

        System.out.println("find = " + find);
        Assertions.assertThat(post).isEqualTo(find);
    }

}