package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import toy.board.domain.dto.ApiResponse;
import toy.board.domain.dto.PagedPostResponse;
import toy.board.service.PostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse> getPosts(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(defaultValue = "createdDate") String sort) {

        PagedPostResponse data = postService.findAll(page, size, sort);

        ApiResponse.<PagedPostResponse>success()
                .status(HttpStatus.OK.value())
                .message("게시글 목록 응답 성공")
                .data(data);

//        return ResponseEntity.ok(response);
        return null;
    }
}
