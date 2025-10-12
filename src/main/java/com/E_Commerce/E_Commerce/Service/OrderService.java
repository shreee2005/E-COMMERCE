package com.E_Commerce.E_Commerce.Service;

import com.E_Commerce.E_Commerce.Model.Order;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface OrderService {
    Order createOrderFromCart(UserDetails userDetails , String ShippingAddress);

    List<Order> getOrderHistoryForUser(UserDetails userDetails);

    Order verifyPaymentAndUpdateStatus(String razorpayOrderId, String razorpayPaymentId);

    Order saveOrder(Order dbOrder);
}
