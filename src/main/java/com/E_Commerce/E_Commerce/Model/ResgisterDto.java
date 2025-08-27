package com.E_Commerce.E_Commerce.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResgisterDto {
    private String firstName;
    private String lastName;
    private String username;
    private String Email;
    private String Password;

}