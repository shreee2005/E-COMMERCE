package com.E_Commerce.E_Commerce.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {
    private String productName;
    private int quantity;
    private BigDecimal price;
}