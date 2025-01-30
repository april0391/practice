package dev.order.domain;

import lombok.Data;

@Data
public class Address {
    private String city;
    private String details;
    private int zipcode;
}
