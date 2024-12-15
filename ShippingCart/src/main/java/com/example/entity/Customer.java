package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String customer_id;
    private String name;
    private String password;
    private int age;
    private String job;
    private String grade;
    private int point;

}
