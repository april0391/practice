package dev.order.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRequest implements Serializable {

    private Long id;
    private String productName;
    private int quantity;

    private Address address;
}
