package toy.board.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toy.board.controller.UserController;
import toy.board.domain.dto.BasicErrorResponseDetail;
import toy.board.domain.dto.response.SignupErrorResponseDetail;
import toy.board.exception.SignupException;
import toy.board.exception.UserException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {

    private final MessageSource ms;

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException e) {
        UserException.UserErrorCode userErrorCode = e.getUserErrorCode();
        BasicErrorResponseDetail body = new BasicErrorResponseDetail();
        switch (userErrorCode) {
            case ID_NOT_FOUND -> {
                body.setCode(userErrorCode);
                body.setMessage("아이디");
            }
        }
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignupException.class)
    public ResponseEntity<SignupErrorResponseDetail> handleSignupException(SignupException e) {
        SignupException.SignupErrorCode signupErrorCode = e.getSignupErrorCode();
        SignupErrorResponseDetail body = new SignupErrorResponseDetail();
        body.setCode(signupErrorCode);

        switch (signupErrorCode) {
            case BINDING -> {
                body.setMessage(ms.getMessage("userSignupRequest.validation", null, null));
                body.setDetail(handleBindingResult(e));
            }
            case DUPLICATE -> {
                body.setMessage(ms.getMessage("userSignupRequest.duplicate", null, null));
                body.setDetail(e.getDuplicateFields());
            }
        }
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> handleBindingResult(SignupException e) {
        Map<String, List<String>> fieldErrorMap = new HashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            String field = fieldError.getField();
            String message = ms.getMessage(fieldError,null);

            if (!fieldErrorMap.containsKey(field)) {
                fieldErrorMap.put(field, new ArrayList<>());
            }
            fieldErrorMap.get(field).add(message);
        }
        return fieldErrorMap;
    }
}
