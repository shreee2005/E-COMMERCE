package com.E_Commerce.E_Commerce.Service;

import com.E_Commerce.E_Commerce.Model.Address;
import com.E_Commerce.E_Commerce.Model.User;
import com.E_Commerce.E_Commerce.dto.AddressDto;
import com.E_Commerce.E_Commerce.dto.UserDto;
import com.E_Commerce.E_Commerce.dto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    User findUserByUsername(String username);

    UserDto getUserProfile(String username);

    UserDto updateUserProfile(String username , UserUpdateRequestDto userUpdateRequestDto);

    List<AddressDto> getUserAddresses(String username);

    AddressDto addUserAddress(String username , AddressDto addressDto);

}
