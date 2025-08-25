package edu.demo.controller;

import edu.demo.model.ProductJwtToken;
import edu.demo.service.GenerateTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final GenerateTokenService generateTokenService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/get-token")
    public String getToken(ProductJwtToken productJwtToken) {
//        System.out.println("productJwtToken = " + productJwtToken);
        String token = generateTokenService.generateToken(productJwtToken);
        System.out.println("token = " + token);
        return null;
    }
}
