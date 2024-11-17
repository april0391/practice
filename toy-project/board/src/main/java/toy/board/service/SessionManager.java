package toy.board.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import toy.board.domain.entity.User;

public interface SessionManager {

    void createSession(User user, Object sessionStore);

    Object getSessionData(Object sessionStore);

    void invalidateSession(HttpServletRequest request, HttpServletResponse response);
}
