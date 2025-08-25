package toy.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.board.domain.dto.user.LoginRequest;
import toy.board.domain.dto.user.UserSimpleInfoResponse;
import toy.board.service.AuthService;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserSimpleInfoResponse> login(@RequestBody LoginRequest request) {
        UserSimpleInfoResponse body = authService.loginProcess(request);
        String token = authService.createToken(body.id());
        return ResponseEntity
                .status(200)
                .header("Authorization", token)
                .body(body);
    }

}
