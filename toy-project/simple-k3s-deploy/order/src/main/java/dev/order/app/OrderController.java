package dev.order.app;

import dev.order.domain.Order;
import dev.order.domain.OrderRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final RestTemplate restTemplate;
    private final Environment env;

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/orders")
    public String order(@RequestBody OrderRequest request) {
        Order order = new Order();
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setAddress(request.getAddress());

        boolean paid = restTemplate.postForObject(env.getProperty("payment-service.url") + "/payment", order, Boolean.class);
        order.setPaid(paid);
        orderRepository.save(order);
        if (paid) {
            // 배송 서비스
        }
        return paid ? "결제 완료: " + order.getId() : "결제 실패: " + order.getId();
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        log.info("POST /orders");
        return orderRepository.findAll();
    }

    @GetMapping("/env/{key}")
    public String getEnv(@PathVariable String key) {
        return Objects.toString(env.getProperty(key), key + "는 null 입니다.");
    }

    @ExceptionHandler(RestClientException.class)
    public String handleRestClientException(RestClientException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

}
