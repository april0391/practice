package edu.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private SecretKey secretKey;
    private JwtParser jwtParser;

    public JwtUtils(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
        jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();
    }

    public List<String> getProducts(String token) {
        Claims claims = jwtParser
                .parseSignedClaims(token)
                .getPayload();
        List<?> productsGeneric = claims.get("products", List.class);
        List<String> products = new ArrayList<>();
        for (Object o : productsGeneric) {
            String product = (String) o;
            products.add(product);
        }
        return products;
    }

    public boolean isExpired(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String generateToken(List<String> products, String orderId) {
        Instant now = Instant.now();  // 현재 시간 (UTC)
        Instant expiry = now.plusMillis(30 * 60 * 1000); // 만료 시간 설정

        return Jwts.builder()
                .claim("products", products)
                .claim("orderId", orderId)
                .issuedAt(Date.from(now))  // Date.from()을 사용해 Instant를 Date로 변환
                .expiration(Date.from(expiry))  // 만료 시간을 설정
                .signWith(secretKey)
                .compact();
    }

}
