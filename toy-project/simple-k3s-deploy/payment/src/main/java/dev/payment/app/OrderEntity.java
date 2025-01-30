package dev.payment.app;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEntity implements Serializable {

    private Long id;
    private String productName;
    private int quantity;
    private boolean paid;
}
