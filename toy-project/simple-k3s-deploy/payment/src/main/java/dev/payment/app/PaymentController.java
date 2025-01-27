package dev.payment.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final RestTemplate restTemplate;
    private final Environment env;

    @PostMapping("/payment")
    public String payment(@RequestBody Map<String, Long> payload) {
        long id = payload.get("id");
        if (id % 4 == 0) {
            throw new RuntimeException();
        }
        return "ok";
    }

    @GetMapping("/check")
    public String check() {
        try {
            return restTemplate.getForObject(env.getProperty("order.host") + "/check", String.class);
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping("/env/{key}")
    public String getEnv(@PathVariable String key) {
        return Objects.toString(env.getProperty(key), key + "는 null 입니다.");
    }
}
