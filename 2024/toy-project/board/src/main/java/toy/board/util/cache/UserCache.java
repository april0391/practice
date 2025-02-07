package toy.board.util.cache;

import toy.board.domain.entity.User;

public interface UserCache {

    void putUser(User user);

    User getUser(Long id);

    void removeUser(Long id);

}
