package edu.jwtyummi.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;
    private JwtParser jwtParser;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public String getUsername(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload().get("username", String.class);
    }

    public String getRoles(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload().get("roles", String.class);
    }

    public Boolean isExpired(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String generateJwtToken(String username, String role, Long expiredMs) {
        Instant now = Instant.now();  // 현재 시간 (UTC)
        Instant expiry = now.plusMillis(expiredMs);  // 만료 시간 설정

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(Date.from(now))  // Date.from()을 사용해 Instant를 Date로 변환
                .expiration(Date.from(expiry))  // 만료 시간을 설정
                .signWith(secretKey)
                .compact();
    }


}
