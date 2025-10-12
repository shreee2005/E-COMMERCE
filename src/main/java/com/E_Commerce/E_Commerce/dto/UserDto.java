package com.E_Commerce.E_Commerce.dto;

import com.E_Commerce.E_Commerce.Model.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private List<AddressDto> addresses;
}
