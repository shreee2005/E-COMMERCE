package com.E_Commerce.E_Commerce.Controller;

import com.E_Commerce.E_Commerce.Model.Order;
import com.E_Commerce.E_Commerce.Model.OrderItem;
import com.E_Commerce.E_Commerce.Service.OrderService;
import com.E_Commerce.E_Commerce.dto.*;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    public OrderController(OrderService orderService, RazorpayClient razorpayClient) {
        this.orderService = orderService;
        this.razorpayClient = razorpayClient;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderRequestDto orderRequestDto) {

        // 1. Create order in our DB with PENDING status
        Order dbOrder = orderService.createOrderFromCart(userDetails, orderRequestDto.getShippingAddress());

        try {

            JSONObject orderRequest = new JSONObject();

            long amountInPaisa = dbOrder.getOrderPrice().multiply(new java.math.BigDecimal(100)).longValue();
            orderRequest.put("amount", amountInPaisa);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid_" + dbOrder.getId());

            com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);
            String razorpayOrderId = razorpayOrder.get("id");

            // 3. Save Razorpay Order ID in our DB
            dbOrder.setRazorpayOrderId(razorpayOrderId);
            orderService.saveOrder(dbOrder);

            // 4. Create response for the frontend
            OrderCreationResponseDto response = new OrderCreationResponseDto();
            response.setDbOrderId(dbOrder.getId());
            response.setRazorpayOrderId(razorpayOrderId);
            response.setAmount(dbOrder.getOrderPrice().doubleValue());
            response.setCurrency("INR");
            response.setApiKey(razorpayKeyId);

            return ResponseEntity.ok(response);

        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Razorpay order.");
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(
            @RequestBody PaymentVerificationRequest verificationRequest) { //

        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", verificationRequest.getRazorpayOrderId());
            options.put("razorpay_payment_id", verificationRequest.getRazorpayPaymentId());
            options.put("razorpay_signature", verificationRequest.getRazorpaySignature());

            boolean isValidSignature = Utils.verifyPaymentSignature(options, this.razorpaySecret);

            if (isValidSignature) {
                // Signature is valid, update order status
                orderService.verifyPaymentAndUpdateStatus(
                        verificationRequest.getRazorpayOrderId(),
                        verificationRequest.getRazorpayPaymentId());

                return ResponseEntity.ok(Map.of("status", "success", "message", "Payment verified and order confirmed."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", "failure", "message", "Payment verification failed."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error during payment verification.");
        }
    }
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<Order> orders = orderService.getOrderHistoryForUser(userDetails);
        List<OrderResponseDto> responseDtos = orders.stream()
                .map(this::mapToOrderResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    // --- Helper methods to map Entities to DTOs ---
    private OrderResponseDto mapToOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus().name());
        dto.setOrderPrice(order.getOrderPrice());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setOrderItems(order.getOrderItems().stream()
                .map(this::mapToOrderItemResponseDto)
                .collect(Collectors.toList()));
        return dto;
    }

    private OrderItemResponseDto mapToOrderItemResponseDto(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }
}