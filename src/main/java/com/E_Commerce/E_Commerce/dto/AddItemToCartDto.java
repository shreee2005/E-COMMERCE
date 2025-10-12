package com.E_Commerce.E_Commerce.dto;

import lombok.Data;

@Data
public class AddItemToCartDto {
    private Long productId;
    private int quantity;
}
