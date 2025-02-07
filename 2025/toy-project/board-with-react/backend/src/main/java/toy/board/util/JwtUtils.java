package toy.board.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import toy.board.exception.AuthException;

import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET_KEY = "test-secret-key";
    private final String ISSUER = "toy-board";
    private final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
            .withIssuer(ISSUER)
            .build();

    public String createToken(String subject) {
        Date now = new Date();
        return JWT.create()
                .withIssuer(ISSUER) // 발급자
                .withSubject(subject) // 사용자 정보
                .withIssuedAt(now) // 발급 시간
                .withExpiresAt(new Date(now.getTime() + 1000 * 60 * 5)) // 만료 시간
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 서명 알고리즘 및 키
    }

    public void verifyToken(String token) {
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AuthException(AuthException.ErrorCode.WRONG_TOKEN_VALUE, e);
        }
    }

    public String getSubject(String token) {
        DecodedJWT verify = verifier.verify(token);
        return verify.getSubject();
    }


}
