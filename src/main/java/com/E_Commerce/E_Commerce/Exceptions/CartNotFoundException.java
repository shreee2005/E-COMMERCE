package com.E_Commerce.E_Commerce.Exceptions;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(String message) {
        super(message);
    }
}
