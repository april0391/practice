package toy.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import toy.board.domain.dto.response.PagedPostResponse;
import toy.board.domain.dto.response.PostDto;
import toy.board.domain.entity.PostEntity;
import toy.board.mapper.PostMapper;
import toy.board.repository.PostRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PagedPostResponse findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<PostEntity> pageResult = postRepository.findAll(pageable);

        List<PostDto> postDtoList = pageResult.stream()
                .map(entity -> postMapper.entityToResponse(entity))
                .toList();
        PagedPostResponse.PageInfo pageInfo = getPageInfo(pageResult);

        return new PagedPostResponse(postDtoList, pageInfo);
    }

    private PagedPostResponse.PageInfo getPageInfo(Page<PostEntity> pageResult) {
        return new PagedPostResponse.PageInfo(
                pageResult.getNumber(),  // 현재 페이지 번호 (0부터 시작)
                pageResult.getSize(),  // 페이지 크기
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );
    }
}
