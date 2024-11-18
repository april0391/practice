package toy.board.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import toy.board.domain.entity.User;

public class HttpSessionSessionManager implements SessionManager {

    @Override
    public void createSession(User user, Object sessionStore) {
        HttpServletRequest request = (HttpServletRequest) sessionStore;
        HttpSession session = request.getSession();
        session.setAttribute("signin", user);
    }

    @Override
    public Object getSessionData(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute("signin");
        }
        return null;
    }

    @Override
    public void invalidateSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

}
