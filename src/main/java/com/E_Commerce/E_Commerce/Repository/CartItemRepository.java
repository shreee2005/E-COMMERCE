package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
