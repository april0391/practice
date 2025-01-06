package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.board.domain.dto.PostResponse;
import toy.board.mapper.PostMapper;
import toy.board.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<PostResponse> getPostResponseList() {
        return postRepository.findAll()
                .stream()
                .map(entity -> postMapper.entityToResponse(entity))
                .toList();
    }
}
