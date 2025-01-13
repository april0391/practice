package toy.board.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toy.board.controller.UserController;
import toy.board.domain.dto.BasicErrorResponseDetail;
import toy.board.exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {

    private final MessageSource ms;

    @ExceptionHandler(BasicUserException.class)
    public ResponseEntity<BasicErrorResponseDetail> handleBasicUserException(BasicUserException e) {
        return new ResponseEntity<>(createBasicErrorResponseDetail(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SignupException.class)
    public ResponseEntity<BasicErrorResponseDetail> handleSignupException(SignupException e) {
        return new ResponseEntity<>(createBasicErrorResponseDetail(e), HttpStatus.BAD_REQUEST);
    }

    private BasicErrorResponseDetail createBasicErrorResponseDetail(UserException e) {
        BasicErrorCode errorCode = e.getErrorCode();
        BasicErrorResponseDetail body = new BasicErrorResponseDetail();
        body.setCode(errorCode);
        body.setMessage(ms.getMessage(errorCode.getMessageKey(), null, null));
        body.setDetail(resolveDetail(e));
        return body;
    }

    private Object resolveDetail(UserException e) {
        Map<BasicErrorCode, Function<UserException, Object>> detailResolvers = Map.of(
                BasicUserException.ErrorCode.ID_NOT_FOUND, ex -> Map.of("id", ((BasicUserException) ex).getId()),
                SignupException.ErrorCode.DUPLICATE, ex -> ((SignupException) ex).getDuplicateFields(),
                SignupException.ErrorCode.VALIDATION, ex -> handleBindingResult(((SignupException) ex).getBindingResult())
        );

        Function<UserException, Object> function = detailResolvers.get(e.getErrorCode());
        if (function == null) {
            return null;
        }
        return function.apply(e);
    }

    private Map<String, List<String>> handleBindingResult(BindingResult b) {
        Map<String, List<String>> fieldErrorMap = new HashMap<>();

        for (FieldError fieldError : b.getFieldErrors()) {
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
