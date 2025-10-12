package com.E_Commerce.E_Commerce.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private Date orderDate;
    private String orderStatus;
    private BigDecimal orderPrice;
    private String shippingAddress;
    private List<OrderItemResponseDto> orderItems;
}