package toy.board.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import toy.board.domain.entity.User;

public class HttpSessionSessionManager implements SessionManager {

    @Override
    public void createSession(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("signin", user);
    }

    @Override
    public void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
