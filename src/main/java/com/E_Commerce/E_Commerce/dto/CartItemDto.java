package com.E_Commerce.E_Commerce.dto;
import lombok.Data;

@Data
public class CartItemDto {
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private String imageUrl;
}
