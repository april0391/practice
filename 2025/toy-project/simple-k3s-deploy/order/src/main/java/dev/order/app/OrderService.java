package dev.order.app;

import dev.order.domain.OrderRequest;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final RabbitTemplate rabbitTemplate;
    private final Queue orderQueue;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired
    public OrderService(RabbitTemplate rabbitTemplate, Queue orderQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.orderQueue = orderQueue;
    }

    public void sendOrder(OrderRequest order) {
        rabbitTemplate.convertAndSend(orderQueue.getName(), order);
        System.out.println("Sent order: " + order);
    }
}
