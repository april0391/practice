package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import toy.board.domain.dto.SearchCond;
import toy.board.domain.entity.Post;
import toy.board.service.PostService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

//    @GetMapping
    public String showPostList(SearchCond cond, Model model) {
        List<Post> posts = postService.findAll(cond);
        /*for (Post post : posts) {
            post.setUser(new User(null, null, post.getNickname()));
        }*/
        model.addAttribute("posts", posts);

        return "posts/post-list";
    }

    @GetMapping
    public String showPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            SearchCond cond,
            Model model) {
        Page<Post> postPage = postService.findAll(cond, page, size);
        model.addAttribute("postPage", postPage);
        model.addAttribute("posts", postPage.getContent());
        return "posts/post-list";
    }

}
