package edu.demo.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    JwtUtils jwtUtils = new JwtUtils("asdoghiwehfwfwdfkjlwadfvnlwsdgsdakdf");

    @Test
    void generateToken() {
        List<String> products = List.of("apple", "banana");
        String orderId = "1234";
        String token = jwtUtils.generateToken(products, orderId);
        System.out.println("token = " + token);
    }

    @Test
    void getProducts() {
        List<String> products = jwtUtils.getProducts("eyJhbGciOiJIUzI1NiJ9.eyJwcm9kdWN0cyI6WyJhcHBsZSIsImJhbmFuYSJdLCJvcmRlcklkIjoiMTIzNCIsImlhdCI6MTcyNzYyMjA3MiwiZXhwIjoxNzI3NjIzODcyfQ.716FTMj8cpfhl2azxJdwvuN2jneGcwjwEyipNk_QLl0");
        for (String product : products) {
            System.out.println("product = " + product);
        }
    }

}