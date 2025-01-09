package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.board.domain.dto.UserSignupRequest;
import toy.board.domain.dto.UserSignupResponse;
import toy.board.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserSignupResponse> signup(@RequestBody @Validated UserSignupRequest request) {
        UserSignupResponse body = userService.signup(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

}
