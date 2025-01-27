package dev.order.app;

import lombok.Data;

@Data
public class OrderEntity {

    private Long id;
    private final String productName;
    private final Integer quantity;
    private boolean paid;

}
