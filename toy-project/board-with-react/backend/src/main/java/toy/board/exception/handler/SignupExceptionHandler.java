package toy.board.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toy.board.controller.UserController;
import toy.board.domain.dto.response.SignupErrorResponse;
import toy.board.exception.SignupException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice(basePackageClasses = UserController.class)
public class SignupExceptionHandler {

    private final MessageSource ms;

    @ExceptionHandler(SignupException.class)
    public ResponseEntity<SignupErrorResponse> handleUserException(SignupException e) {
        SignupException.ErrorCode errorCode = e.getErrorCode();
        SignupErrorResponse body = new SignupErrorResponse();
        body.setCode(errorCode);

        switch (errorCode) {
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
