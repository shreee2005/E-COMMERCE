package com.E_Commerce.E_Commerce.Controller;


import com.E_Commerce.E_Commerce.Service.CartService;
import com.E_Commerce.E_Commerce.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.E_Commerce.E_Commerce.Model.User;
import com.E_Commerce.E_Commerce.Repository.UserRepository;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Long userId = getUserIdFromUserDetails(userDetails);
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }


    @PostMapping("/items")
    public ResponseEntity<CartDto> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddItemToCartDto itemDto) {
        Long userId = getUserIdFromUserDetails(userDetails);
        return ResponseEntity.ok(cartService.addItemToCart(userId, itemDto));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartDto> removeItemFromCart(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        Long userId = getUserIdFromUserDetails(userDetails);
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, productId));
    }

    private Long getUserIdFromUserDetails(UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getId();
    }
}