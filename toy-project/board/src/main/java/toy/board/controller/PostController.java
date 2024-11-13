package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;
import toy.board.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String showPostList(SearchCond cond, Model model) {
        System.out.println("cond = " + cond);
        List<Post> posts = postService.findAll(cond);
        model.addAttribute("posts", posts);
        return "post/post-list";
    }

}
