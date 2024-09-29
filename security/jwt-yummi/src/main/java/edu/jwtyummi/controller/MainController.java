package edu.jwtyummi.controller;

import edu.jwtyummi.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainP(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        String roles = principal.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.joining(","));
        return username + " " + roles;*/
        return customUserDetails.getUsername();
    }

}
