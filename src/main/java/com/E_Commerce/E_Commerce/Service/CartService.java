package com.E_Commerce.E_Commerce.Service;

import com.E_Commerce.E_Commerce.Model.Cart;
import com.E_Commerce.E_Commerce.Model.CartItem;
import com.E_Commerce.E_Commerce.dto.AddItemToCartDto;
import com.E_Commerce.E_Commerce.dto.CartDto;
import com.E_Commerce.E_Commerce.dto.CartItemDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartDto addItemToCart(Long userId , AddItemToCartDto itemDto);
    CartDto getCartForUser(Long userId);
    CartDto removeItemFromCart(Long userId , Long productId);

}
