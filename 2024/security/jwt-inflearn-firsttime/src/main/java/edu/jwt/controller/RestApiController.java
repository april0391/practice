package edu.jwt.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class RestApiController {

    @GetMapping("/")
    @ResponseBody
    public String basic() {
        return "ok";
    }

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() {
        return "<h1>token</h1>";
    }

}
