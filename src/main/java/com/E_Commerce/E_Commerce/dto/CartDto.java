package com.E_Commerce.E_Commerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private List<CartItemDto> items;
    private double totalPrice;
}
