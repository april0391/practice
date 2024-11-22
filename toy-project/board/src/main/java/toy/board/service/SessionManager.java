package toy.board.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import toy.board.domain.entity.User;

public interface SessionManager {

    void createSession(User user, HttpServletRequest request, HttpServletResponse response);

    Object getSession(HttpServletRequest request, HttpServletResponse response);

    void invalidateSession(HttpServletRequest request, HttpServletResponse response);
}
