package com.E_Commerce.E_Commerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String email;
    private String firstName;
    private String lastName;

}