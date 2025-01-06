package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toy.board.domain.dto.ApiResponse;
import toy.board.service.PostService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse getPosts() {
        return ApiResponse.success()
                .status("success")
                .message("message")
                .data(postService.getPostResponseList());
    }
}
