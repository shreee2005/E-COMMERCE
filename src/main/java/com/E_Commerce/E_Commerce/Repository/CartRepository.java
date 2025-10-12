package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.classfile.Opcode;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserId(Long userId);
}
