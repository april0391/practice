package toy.board.repository.mybatis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.entity.Post;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryMybatisTest {

    @Autowired
    PostRepositoryMybatis postRepository;

    @Transactional
    @Test
    void save() {
        Post post = new Post();
        post.setTitle("제목");
        post.setContent("내용");
        post.setUserId(1L);
        postRepository.save(post);

        Post find = postRepository.findById(post.getId()).get();
        Assertions.assertThat(post).isEqualTo(find);
    }



}