package edu.demo.model;

import lombok.*;

import java.util.List;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductJwtToken {

    private List<String> products;
    private String orderId;

}
