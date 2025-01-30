package dev.payment.app;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentListener {

    @RabbitListener(queues = "order-queue")
    public void processOrder(OrderEntity order) {
        System.out.println("Received order: " + order);
    }

}
