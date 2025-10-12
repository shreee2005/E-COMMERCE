package com.E_Commerce.E_Commerce.dto;

import lombok.Data;

@Data // Using Lombok to generate getters, setters, constructors, etc.
public class OrderCreationResponseDto {

    private Long dbOrderId;
    private String razorpayOrderId;
    private double amount;
    private String currency;
    private String apiKey;

}