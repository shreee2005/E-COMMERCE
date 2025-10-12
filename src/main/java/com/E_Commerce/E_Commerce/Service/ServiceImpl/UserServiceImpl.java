package com.E_Commerce.E_Commerce.Service.ServiceImpl;

import com.E_Commerce.E_Commerce.Model.Address;
import com.E_Commerce.E_Commerce.Model.User;
import com.E_Commerce.E_Commerce.Repository.UserRepository;
import com.E_Commerce.E_Commerce.Service.UserService;
import com.E_Commerce.E_Commerce.dto.AddressDto;
import com.E_Commerce.E_Commerce.dto.UserDto;
import com.E_Commerce.E_Commerce.dto.UserUpdateRequestDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) {
       return userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return convertToUserDto(user);
    }

    @Override
    public UserDto updateUserProfile(String username, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        user.setEmail(userUpdateRequestDto.getEmail());
        user.setFirstName(userUpdateRequestDto.getFirstName());
        user.setLastName(userUpdateRequestDto.getLastName());

        User updatedUser = userRepository.save(user);
        return convertToUserDto(updatedUser);
    }

    @Override
    public List<AddressDto> getUserAddresses(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return user.getAddresses().stream().map(this::convertAddressToDto).collect(Collectors.toList());
    }

    @Override
    public AddressDto addUserAddress(String username, AddressDto addressDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPostalCode(addressDto.getPostalCode());
        address.setCountry(addressDto.getCountry());
        address.setUser(user);
        return convertAddressToDto(address);
    }

    public UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        userDto.setAddresses(
                user.getAddresses().stream()
                        .map(this::convertAddressToDto)
                        .collect(Collectors.toList())
        );
        return userDto;
    }

    public AddressDto convertAddressToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        address.setId(address.getId());
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setPostalCode(addressDto.getPostalCode());
        addressDto.setCity(address.getCountry());
        return addressDto;
    }
}
