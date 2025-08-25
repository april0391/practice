package toy.board.service;

import toy.board.domain.entity.User;

public final class SessionContextHolder {

    private static final ThreadLocal<User> sessionContextHolder = new ThreadLocal<>();

    public static void setSession(User user) {
        sessionContextHolder.set(user);
    }

    public static User getSession() {
        return sessionContextHolder.get();
    }

    public static void clearSession() {
        sessionContextHolder.remove();
    }
}
