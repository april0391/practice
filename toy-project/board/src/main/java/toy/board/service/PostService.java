package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.PostForm;
import toy.board.domain.dto.PostWithUserDto;
import toy.board.domain.entity.Post;
import toy.board.domain.entity.User;
import toy.board.repository.PostRepository;
import toy.board.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post save(PostForm postForm) {
        Post post = dtoToEntity(postForm);
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public PostWithUserDto findById_query(Long id) {
        return postRepository.findById_query(id).orElseThrow();
    }

    public List<PostWithUserDto> findAll() {
        return postRepository.findAll();
    }

    private Post dtoToEntity(PostForm postForm) {
        User user = userRepository.findById(postForm.getUserId()).orElseThrow();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return new Post(postForm.getTitle(),
                postForm.getContent(),
                now,
                now,
                user);
    }
}
