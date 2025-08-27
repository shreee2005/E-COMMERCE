package com.E_Commerce.E_Commerce.Controller;

import com.E_Commerce.E_Commerce.Model.ResgisterDto; // Corrected class name
import com.E_Commerce.E_Commerce.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // Use constructor injection to get the AuthService instance
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // This is line 18 where the error occurred
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ResgisterDto resgisterDto) {
        // 'authService' will no longer be null
        String response = authService.register(resgisterDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}