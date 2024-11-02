package toy.board.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptTest {

    @Test
    void test() {
        String rawPw = "abcd1234!";
        String salt = BCrypt.gensalt();
        String hashedPw = BCrypt.hashpw(rawPw, salt);

        boolean checkPw = BCrypt.checkpw(rawPw, hashedPw);

        Assertions.assertThat(checkPw).isTrue();
    }
}
