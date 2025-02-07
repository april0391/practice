package com.example.demo.init;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final Environment env;

    @GetMapping
    public String home() {
        return "This is Payment Service! home()";
    }

    @GetMapping("/payment")
    public String payment() {
        return "This is Payment Service! payment()";
    }

    @PostMapping("/payment/{orderNumber}")
    public String paymentProcess(@PathVariable String orderNumber) {
        return "processed orderNumber " + orderNumber;
    }

    @GetMapping("/env/{key}")
    public String print(@PathVariable String key) {
        String property = env.getProperty(key);
        return property != null ? property : "null";
    }
}
