package toy.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import toy.board.domain.entity.User;
import toy.board.exception.UserException;
import toy.board.service.SessionManager;

import java.io.IOException;

@RequiredArgsConstructor
@ControllerAdvice
public class AppControllerAdvice {

    private final SessionManager sessionManager;

    @ModelAttribute
    public void model(HttpServletRequest request, HttpServletResponse response, Model model) {
        User sessionData = (User) sessionManager.getSession(request, response);
        model.addAttribute("user", sessionData);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public void handleUserException(UserException e, HttpServletResponse response) throws IOException {
        switch (e.getErrorCode()) {
            case PASSWORD_INCORRECT ->
                    response.getWriter().write("<script>alert('Incorrect Password.');location.href='/signin';</script>");

            case USERNAME_INVALID ->
                    response.getWriter().write("<script>alert('Invalid Username.');location.href='/signin';</script>");
        }
    }

}
