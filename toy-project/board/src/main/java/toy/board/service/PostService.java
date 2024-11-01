package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.board.domain.dto.PostDto;
import toy.board.domain.entity.Post;
import toy.board.domain.entity.User;
import toy.board.repository.PostRepository;
import toy.board.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Post> getPosts() {
        return postRepository.getPosts();
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow();
    }

    public Post save(PostDto postDto) {
        Post post = dtoToEntity(postDto);
        return postRepository.save(post);
    }

    private Post dtoToEntity(PostDto postDto) {
        User user = userRepository.findById(postDto.getUserId()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        return new Post(postDto.getTitle(),
                postDto.getContent(),
                now,
                now,
                user);
    }
}
