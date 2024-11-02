package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toy.board.service.PostService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String getPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "post/post-list";
    }

}
