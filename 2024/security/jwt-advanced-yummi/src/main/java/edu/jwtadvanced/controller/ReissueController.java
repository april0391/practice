package edu.jwtadvanced.controller;

import edu.jwtadvanced.entity.Refresh;
import edu.jwtadvanced.repository.RefreshRepository;
import edu.jwtadvanced.security.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReissueController {

    private final JwtUtils jwtUtils;
    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getRefreshToken(request);
        if (refreshToken == null) {
            return new ResponseEntity<>("refresh token is null", HttpStatus.BAD_REQUEST);
        }
        try {
            jwtUtils.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token is expired", HttpStatus.BAD_REQUEST);
        }
        String category = jwtUtils.getCategory(refreshToken);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }
        if (!refreshRepository.existsByRefresh(refreshToken)) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }
        String username = jwtUtils.getUsername(refreshToken);
        String roles = jwtUtils.getRoles(refreshToken);
        String accessToken = jwtUtils.generateJwtToken("access", username, roles, 30 * 60 * 1000L);
        String newRefreshToken = jwtUtils.generateJwtToken("refresh", username, roles, 24 * 60 * 60 * 1000L);

        refreshRepository.deleteByRefresh(refreshToken);
        Refresh refresh = new Refresh();
        refresh.setUsername(username);
        refresh.setRefresh(newRefreshToken);
        refreshRepository.save(refresh);

        Cookie cookie = new Cookie("refresh", newRefreshToken);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("access", accessToken)
                .build();
    }

    private String getRefreshToken(HttpServletRequest request) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        return refreshToken;
    }

}
