package com.E_Commerce.E_Commerce.dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String description; // Corrected spelling
    private int price; // Changed to int for simplicity
    private int stockQuantity;
    private String imageUrl;

    @NonNull
    private Long categoryId; // The ID of the category to associate with
}
