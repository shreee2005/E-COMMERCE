package com.E_Commerce.E_Commerce;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretGenerator {
    public static void main(String[] args) {
        // Create a secure random byte array (32 bytes = 256 bits)
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);

        // Encode the byte array to a Base64 string
        String base64Key = Base64.getEncoder().encodeToString(key);

        System.out.println("--- Your New Base64 JWT Secret ---");
        System.out.println(base64Key);
    }
}
