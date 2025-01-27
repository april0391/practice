package dev.order.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final Map<Long, OrderEntity> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong();

    private final RestTemplate restTemplate;
    private final Environment env;

    @PostMapping("/order-service/orders")
    public String orderTemp() {
        return "ok";
    }

    @PostMapping("/orders")
    public String order(@RequestBody OrderEntity orderEntity) {
        log.info("GET /orders");
        long id = seq.incrementAndGet();
        orderEntity.setId(id);

        Map<String, Long> requestBody = new HashMap<>();
        requestBody.put("id", id);
        store.put(id, orderEntity);

        try {
            restTemplate.postForEntity(env.getProperty("payment.host") + "/payment", requestBody, String.class);
            orderEntity.setPaid(true);
            return "주문 성공. id: " + id;

        } catch (RestClientException e) {
            System.out.println("e = " + e);
            return "결제 대기. id: " + id;
        }
    }

    @GetMapping("/orders")
    public List<OrderEntity> getOrders() {
        log.info("POST /orders");
        return new ArrayList<>(store.values());
    }

    @GetMapping("/check")
    public String check() {
        log.info("check()");
        return "Check Payment -> Order Ok";
    }

    @GetMapping("/env/{key}")
    public String getEnv(@PathVariable String key) {
        return Objects.toString(env.getProperty(key), key + "는 null 입니다.");
    }

}
