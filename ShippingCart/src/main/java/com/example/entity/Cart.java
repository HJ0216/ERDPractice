package com.example.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int order_id;
    private String customer_id;
    private int product_id;
    private int quantity;
    private Date orderDate;

    private String name;
    private int stock;
    private int price;
    private String manufacturer;

    private int amount;
}
