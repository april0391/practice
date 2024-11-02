package toy.board.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toy.board.domain.dto.PostForm;
import toy.board.domain.dto.PostWithUserDto;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.Post;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach() {
        UserForm dto = new UserForm();
        dto.setUsername("tester");
        dto.setRawPassword("1234");
        dto.setName("John");
        dto.setEmail("tester@tester.com");
        dto.setNickname("spring");
        userService.save(dto);
    }

    @Test
    void save() {
        PostForm dto = formInit();
        Post saved = postService.save(dto);
        System.out.println("saved = " + saved);
        Post find = postService.findById(saved.getId());
        System.out.println("find = " + find);

        assertThat(saved).isEqualTo(find);
    }

    @Test
    void queryTest() {
        PostForm form = formInit();
        Post saved = postService.save(form);
        System.out.println("saved = " + saved);

        PostWithUserDto postWithUserDto = postService.findById_query(saved.getId());
        System.out.println("postWithUserDto = " + postWithUserDto);
    }

    PostForm formInit() {
        PostForm dto = new PostForm();
        dto.setTitle("title");
        dto.setContent("content");
        dto.setUserId(1L);
        return dto;
    }


}