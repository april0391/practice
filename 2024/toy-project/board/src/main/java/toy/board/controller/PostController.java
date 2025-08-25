package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toy.board.domain.dto.PostForm;
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
    public String showPostListPage(SearchCond cond, Model model) {
        List<Post> posts = postService.findAll(cond);
        /*for (Post post : posts) {
            post.setUser(new User(null, null, post.getNickname()));
        }*/
        model.addAttribute("posts", posts);

        return "posts/post-list";
    }

    @GetMapping
    public String showPostListPage(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               SearchCond cond, Model model) {
        Page<Post> postPage = postService.findAll(cond, page - 1, size);
        model.addAttribute("postPage", postPage);
        model.addAttribute("posts", postPage.getContent());
        return "posts/post-list";
    }

    @GetMapping("/{id}")
    public String showPostDetailPage(@PathVariable Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "posts/post-detail";
    }

    @GetMapping("/new")
    public String showPostNewPage() {
        return "posts/post-new";
    }

    @PostMapping
    public String registerPost(PostForm postForm) {
        postService.save(postForm);
        return "redirect:/posts";
    }

}
