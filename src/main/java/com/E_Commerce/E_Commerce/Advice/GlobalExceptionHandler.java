package com.E_Commerce.E_Commerce.Advice;

import com.E_Commerce.E_Commerce.Exceptions.ErrorResponse;
import com.E_Commerce.E_Commerce.Exceptions.InvalidProductToAdd;
import com.E_Commerce.E_Commerce.Exceptions.ProductNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.geom.RectangularShape;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundExeption(ProductNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(404 , "Product not Found " , LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidProductToAdd.class)
    public ResponseEntity<?> handleInvalidProductToAdd(InvalidProductToAdd exception){
        ErrorResponse errorResponse = new ErrorResponse(555 , "Invalid Product To Add" , LocalDateTime.now());
        return new ResponseEntity<>(errorResponse , HttpStatus.BAD_REQUEST);
    }
}
