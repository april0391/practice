package com.example.demo.init;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final Environment env;
    private final RestTemplate restTemplate;

    @GetMapping
    public String home() {
        return "This is Order Service! home()";
    }

    @GetMapping("/order")
    public String order() {
        int orderNumber = new Random().nextInt(100);
        return restTemplate.postForEntity(env.getProperty("payment-service.url") + "/payment/" +orderNumber, null, String.class)
                .getBody();
    }

    @GetMapping("/env/{key}")
    public String print(@PathVariable String key) {
        String property = env.getProperty(key);
        return property != null ? property : "null";
    }

}
