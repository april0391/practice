package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.board.domain.dto.request.UserSignupRequest;
import toy.board.domain.dto.response.SignupSuccessResponse;
import toy.board.exception.SignupException;
import toy.board.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SignupSuccessResponse> signup(@RequestBody @Validated UserSignupRequest request,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SignupException(SignupException.ErrorCode.BINDING, bindingResult);
        }
        SignupSuccessResponse body = userService.signup(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

}
