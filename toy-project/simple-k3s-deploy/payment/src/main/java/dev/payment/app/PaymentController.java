package dev.payment.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final RestTemplate restTemplate;
    private final Environment env;

    @PostMapping("/payment")
    public boolean payment() {
        return Math.random() > 0.25;
    }

    @GetMapping("/env/{key}")
    public String getEnv(@PathVariable String key) {
        return Objects.toString(env.getProperty(key), key + "는 null 입니다.");
    }
}
