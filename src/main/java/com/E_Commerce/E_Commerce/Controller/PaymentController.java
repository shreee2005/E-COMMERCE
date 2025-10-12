package com.E_Commerce.E_Commerce.Controller;


import com.E_Commerce.E_Commerce.Repository.OrderRepository;
import com.E_Commerce.E_Commerce.dto.CreateOrderRequest;
import com.E_Commerce.E_Commerce.dto.PaymentVerificationRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// CORRECTION 1: Changed annotation to @RestController
@RestController
@RequestMapping("/api/payments")
// CORRECTION 2: Renamed class to follow Java conventions
public class PaymentController {

    private final OrderRepository orderRepository;
    private final RazorpayClient razorpayClient;

    public PaymentController(OrderRepository orderRepository, RazorpayClient razorpayClient) {
        this.orderRepository = orderRepository;
        this.razorpayClient = razorpayClient;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            JSONObject orderRequest = new JSONObject();
            long amountInPaisa = (long) (request.getAmount() * 100);
            orderRequest.put("amount", amountInPaisa);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_receipt_" + System.currentTimeMillis());

            Order razorpayOrder = razorpayClient.orders.create(orderRequest);
            String orderId = razorpayOrder.get("id"); // No need to cast to String explicitly
            return ResponseEntity.ok(Map.of("orderId", orderId, "amount", amountInPaisa));

        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error Creating Razorpay Order");
        }
    }

    // CORRECTION 3: Fixed typo in @Value annotation
    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @PostMapping("/verify") // Added the endpoint mapping
    public ResponseEntity<?> verifyPayment(@RequestBody PaymentVerificationRequest request) {
        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", request.getRazorpayOrderId());
            options.put("razorpay_payment_id", request.getRazorpayPaymentId());
            options.put("razorpay_signature", request.getRazorpaySignature());
            boolean isValid = Utils.verifyPaymentSignature(options, this.razorpaySecret);

            if (isValid) {
                // TODO: Add logic here to update your order status in the database
                return ResponseEntity.ok(Map.of("status", "success", "message", "Payment verified successfully"));
            } else {
                return ResponseEntity.status(400).body(Map.of("status", "failure", "message", "Payment verification failed"));
            }
        } catch (Exception e) { // Catching generic exception is safer here
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error During Payment Verification");
        }
    }
}

