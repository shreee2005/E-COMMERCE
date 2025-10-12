package com.E_Commerce.E_Commerce.Controller;

import com.E_Commerce.E_Commerce.Payload.JwtAuthResponseDto;
import com.E_Commerce.E_Commerce.Payload.LoginDto;
import com.E_Commerce.E_Commerce.Security.JwtTokenProvider;
import com.E_Commerce.E_Commerce.dto.ResgisterDto;
import com.E_Commerce.E_Commerce.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Declare all required dependencies as 'private final'
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // The constructor must accept ALL final fields as parameters.
    public AuthController(AuthenticationManager authenticationManager,
                          AuthService authService,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider; // FIX: Initialize the jwtTokenProvider
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody ResgisterDto resgisterDto) {
        String response = authService.register(resgisterDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // FIX: This line will now work correctly because jwtTokenProvider is not null.
        String token = jwtTokenProvider.generateJwtToken(authentication.getName());

        JwtAuthResponseDto response = new JwtAuthResponseDto();
        response.setAccessToken(token);

        return ResponseEntity.ok(response);
    }
}