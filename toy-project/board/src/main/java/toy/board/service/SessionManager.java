package toy.board.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import toy.board.domain.entity.User;

public interface SessionManager {

    void createSession(User user, HttpServletRequest request);

    void invalidateSession(HttpServletRequest request);
}
