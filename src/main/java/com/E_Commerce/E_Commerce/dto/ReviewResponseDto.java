package com.E_Commerce.E_Commerce.dto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.time.LocalDateTime;
@Getter@Setter
@Data
public class ReviewResponseDto {
    private Long id;
    private int rating ;
    private String comment;
    private String username;
    private Long productId;
    private LocalDateTime createdAt;
}
