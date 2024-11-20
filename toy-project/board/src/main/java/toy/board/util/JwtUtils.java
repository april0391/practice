package toy.board.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtils {

    private final String SECRET_KEY = "test-secret-key";
    private final String ISSUER = "toy-board";
    @Getter
    private final JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
            .withIssuer(ISSUER)
            .build();

    public String createToken(String subject, int expirationTimeSecond) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeSecond * 1000L);
        return JWT.create()
                .withIssuer(ISSUER) // 발급자
                .withSubject(subject) // 사용자 정보
                .withIssuedAt(now) // 발급 시간
                .withExpiresAt(expiryDate) // 만료 시간
                .sign(Algorithm.HMAC256(SECRET_KEY)); // 서명 알고리즘 및 키
    }

    public boolean isTokenValid(String token) {
        if (token == null) {
            return false;
        }
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getSubject(String token) {
        DecodedJWT verify = verifier.verify(token);
        return verify.getSubject();
    }
}
