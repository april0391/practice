package toy.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.board.domain.dto.ApiResponse;
import toy.board.domain.dto.UserSignupRequest;
import toy.board.domain.dto.UserSignupResponse;
import toy.board.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse signup(@RequestBody @Validated UserSignupRequest request) {
        UserSignupResponse result = userService.signup(request);

        return ApiResponse.<UserSignupResponse>success()
                .status(HttpStatus.CREATED)
                .message("회원 가입 성공")
                .data(result)
                .build();
    }

}
