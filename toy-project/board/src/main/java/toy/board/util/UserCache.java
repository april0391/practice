package toy.board.util;

import toy.board.domain.entity.User;

public interface UserCache {

    void putSession(User user);

    User getSession(Long id);

    void removeSession(Long id);

}
