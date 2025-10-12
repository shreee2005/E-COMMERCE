package com.E_Commerce.E_Commerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRequest {
    private Long orderId;
    private Double amount;
}
