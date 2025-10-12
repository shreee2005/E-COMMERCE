package com.E_Commerce.E_Commerce.Service.ServiceImpl;

import com.E_Commerce.E_Commerce.Exceptions.CartNotFoundException;
import com.E_Commerce.E_Commerce.Exceptions.ProductNotFoundException;
import com.E_Commerce.E_Commerce.Model.Cart;
import com.E_Commerce.E_Commerce.Model.CartItem;
import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Model.User;
import com.E_Commerce.E_Commerce.Repository.CartRepository;
import com.E_Commerce.E_Commerce.Repository.ProductRepository;
import com.E_Commerce.E_Commerce.Repository.UserRepository;
import com.E_Commerce.E_Commerce.Service.CartService;
import com.E_Commerce.E_Commerce.dto.AddItemToCartDto;
import com.E_Commerce.E_Commerce.dto.CartDto;
import com.E_Commerce.E_Commerce.dto.CartItemDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CartServiceImpl implements CartService {
   private final ProductRepository productRepository;
    private final  CartRepository cartRepository;
    private final  UserRepository userRepository;

    public CartServiceImpl(ProductRepository productRepository, CartRepository cartRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartDto addItemToCart(Long userId , AddItemToCartDto itemDto) {
        Cart cart = getOrCreateCart(userId);
        Product product = productRepository.findById(itemDto.getProductId())
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found"));

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getName().equals(product.getId())).findFirst();

        if(existingItemOpt.isPresent()) {
            CartItem ExistingItem = existingItemOpt.get();
            ExistingItem.setQuantity(ExistingItem.getQuantity() + itemDto.getQuantity());
        }else{
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct (product);
            newItem.setQuantity(itemDto.getQuantity());
            cart.getItems().add(newItem);
        }
        Cart updatedCart = cartRepository.save(cart);
        return mapCartToDto(updatedCart);
    }

    private CartDto mapCartToDto(Cart updatedCart) {
        CartDto cartDto = new CartDto();
        cartDto.setItems(updatedCart.getItems().stream().map(this::mapCartItemToDto).collect(Collectors.toList()));
        cartDto.setTotalPrice(updatedCart.getItems().stream()
                .mapToDouble(item -> item.getQuantity()*item.getProduct().getPrice()).sum());

        return cartDto;
    }

    private CartItemDto mapCartItemToDto(CartItem cartItem){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(cartItem.getProduct().getId());
        cartItemDto.setProductName(cartItem.getProduct().getName());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setPrice(cartItem.getProduct().getPrice());
        cartItemDto.setImageUrl(cartItem.getProduct().getImageUrl());

        return cartItemDto;
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(()->{
            User user = userRepository.findById(userId).orElseThrow(()->new  UsernameNotFoundException("User Not found"));
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
    }

    @Override
    public CartDto getCartForUser(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return mapCartToDto(cart);
    }

    @Override
    public CartDto removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()-> new CartNotFoundException("Cart Not Found for User"));
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        Cart updatedCart = cartRepository.save(cart);
        return mapCartToDto(updatedCart);
    }



}
