package com.E_Commerce.E_Commerce.Exceptions;

public class InvalidProductToAdd extends RuntimeException{
    public InvalidProductToAdd(String message) {
        super(message);
    }
}
