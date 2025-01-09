package toy.board.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import toy.board.exception.UserException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(UserException e) {
        if (e.getErrorCode() == UserException.ErrorCode.DUPLICATE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("dd");
        }
        return null;
    }
}
