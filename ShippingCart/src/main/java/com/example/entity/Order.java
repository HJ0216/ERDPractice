package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int order_id;
    private String customer_id;
    private int product_id;
    private int quantity;
    private Date orderDate;
}
