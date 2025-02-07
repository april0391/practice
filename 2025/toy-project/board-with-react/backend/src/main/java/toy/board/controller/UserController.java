package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toy.board.domain.dto.user.SignupRequest;
import toy.board.domain.dto.user.UserDetailInfoResponse;
import toy.board.domain.dto.user.UserSimpleInfoResponse;
import toy.board.exception.SignupException;
import toy.board.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserSimpleInfoResponse> signup(@RequestBody @Validated SignupRequest request,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SignupException(SignupException.ErrorCode.VALIDATION, bindingResult);
        }
        UserSimpleInfoResponse body = userService.signup(request);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDetailInfoResponse getUserDetailInfo(@PathVariable Long id) {
        return userService.getUserInfo(id);
    }


}
