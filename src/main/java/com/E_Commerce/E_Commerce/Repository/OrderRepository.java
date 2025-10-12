package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.Order;
import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> , JpaSpecificationExecutor<Product> {

    List<Order> findByUserOrderByOrderDateDesc(User user);

    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);
}
