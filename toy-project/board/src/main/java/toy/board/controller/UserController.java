package toy.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import toy.board.domain.dto.UserForm;
import toy.board.domain.entity.User;
import toy.board.service.SessionManager;
import toy.board.service.UserService;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final SessionManager sessionManager;

    @GetMapping("/signin")
    public String showSigninPage() {
        return "users/signin";
    }

    @PostMapping("/signin")
    public String signin(String username, String password,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws IOException {
        User user = userService.signin(username, password);
        sessionManager.createSession(user, response);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "users/signup";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        sessionManager.invalidateSession(request, response);
        return "redirect:/";
    }

    @PostMapping("/users")
    public ResponseEntity<User> signup(@ModelAttribute UserForm userForm) {
        User save = userService.save(userForm);
        return ResponseEntity.status(201).body(save);
    }

}
