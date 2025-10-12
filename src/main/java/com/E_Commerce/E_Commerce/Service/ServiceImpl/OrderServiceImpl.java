package com.E_Commerce.E_Commerce.Service.ServiceImpl;

import com.E_Commerce.E_Commerce.Exceptions.CartNotFoundException;
import com.E_Commerce.E_Commerce.Exceptions.OrderNotFoundException;
import com.E_Commerce.E_Commerce.Model.*;
import com.E_Commerce.E_Commerce.Repository.CartRepository;
import com.E_Commerce.E_Commerce.Repository.OrderRepository;
import com.E_Commerce.E_Commerce.Repository.UserRepository;
import com.E_Commerce.E_Commerce.Service.OrderService;
import jakarta.transaction.Transactional; // Import Transactional
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; // Import Service

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional // Ensures that if any step fails, the whole transaction is rolled back
    public Order createOrderFromCart(UserDetails userDetails, String shippingAddress) {
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("User not Found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CartNotFoundException("Cart is not found for this User"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderItems(new ArrayList<>()); // Initialize the list

        BigDecimal totalPrice = BigDecimal.ZERO;

        // Iterate over a copy of the list to avoid issues while modifying
        for(CartItem cartItem : new ArrayList<>(cart.getItems())){
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(BigDecimal.valueOf(cartItem.getProduct().getPrice()));
            orderItem.setOrder(order);

            // --- FIX 1: Add the created orderItem to the order's list ---
            order.getOrderItems().add(orderItem);

            // --- FIX 2: Use cartItem.getQuantity() for price calculation, not stockQuantity ---
            totalPrice = totalPrice.add(orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        }

        order.setOrderPrice(totalPrice);

        // Clear the items from the cart and save it
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderRepository.save(order);
    }
    @Override
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }
    @Override
    public List<Order> getOrderHistoryForUser(UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    @Override
    public Order verifyPaymentAndUpdateStatus(String razorpayOrderId, String razorpayPaymentId) {
        Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId)
                    .orElseThrow(()->new OrderNotFoundException("Order Not found for razorPayOrderID" + razorpayOrderId));

        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setRazorpayPaymentId(razorpayPaymentId);

        return orderRepository.save(order);
    }


}