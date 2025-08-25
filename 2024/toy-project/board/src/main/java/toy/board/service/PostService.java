package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.board.domain.dto.PostForm;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;
import toy.board.domain.entity.User;
import toy.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post save(PostForm postForm) {
        Post post = mapToEntity(postForm);
        return postRepository.save(post);
    }

    private Post mapToEntity(PostForm form) {
        User user = SessionContextHolder.getSession();
        return new Post(
                form.getTitle(),
                form.getContent(),
                user
        );
    }

    public Post findById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow();
    }

    public List<Post> findAll(SearchCond cond) {
        return postRepository.findAll(cond);
    }

    public Page<Post> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("registeredAt").descending());
        return postRepository.findAll(pageable);
    }

    public Page<Post> findAll(SearchCond cond, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("registeredAt").descending());
        return postRepository.findAll(cond, pageable);
    }

}
