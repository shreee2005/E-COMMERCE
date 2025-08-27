package com.E_Commerce.E_Commerce.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println(passwordEncoder.encode("shriraj_jagtap"));
        System.out.println(passwordEncoder.encode("sahil_jadhav"));

    }
}
