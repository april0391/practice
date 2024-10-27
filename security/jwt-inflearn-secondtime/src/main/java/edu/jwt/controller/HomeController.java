package edu.jwt.controller;

import edu.jwt.model.User;
import edu.jwt.security.PrincipalDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    /*@GetMapping
    @ResponseBody
    public String homeV1(HttpServletResponse response) {
        Cookie cookie = new Cookie("test1", "test1");
        cookie.setPath("/");
        response.addCookie(cookie);
        return "ok";
    }*/

    /*@GetMapping
    @ResponseBody
    public ResponseEntity<String> homeV2() {
        ResponseCookie responseCookie = ResponseCookie.from("test2", "test2")
                .path("/")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body("ok");
    }*/

    @GetMapping
    @ResponseBody
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getUser().toString();
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("HomeController.login");
        return "user/login";
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "ok";
    }

    @GetMapping("/api/v1/manager")
    public String manager() {
        return "ok";
    }

}
