package com.E_Commerce.E_Commerce.Service;

import com.E_Commerce.E_Commerce.dto.ResgisterDto;
import com.E_Commerce.E_Commerce.dto.LoginDto;

public interface AuthService {
    String register(ResgisterDto resgisterDto);
    String login(LoginDto loginDto);
}
