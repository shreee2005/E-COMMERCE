package com.E_Commerce.E_Commerce.Controller;

import com.E_Commerce.E_Commerce.Service.UserService;
import com.E_Commerce.E_Commerce.dto.AddressDto;
import com.E_Commerce.E_Commerce.dto.UserDto;
import com.E_Commerce.E_Commerce.dto.UserUpdateRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users/me")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserProfile(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateCurrentUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserUpdateRequestDto updateRequest) {
        return ResponseEntity.ok(userService.updateUserProfile(userDetails.getUsername(), updateRequest));
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDto>> getUserAddresses(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserAddresses(userDetails.getUsername()));
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressDto> addUserAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddressDto addressDto) {
        AddressDto newAddress = userService.addUserAddress(userDetails.getUsername(), addressDto);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }
}
