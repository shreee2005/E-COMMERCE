package com.E_Commerce.E_Commerce.Service.ServiceImpl;

import com.E_Commerce.E_Commerce.dto.ResgisterDto;
import com.E_Commerce.E_Commerce.Model.Role;
import com.E_Commerce.E_Commerce.Model.User;
import com.E_Commerce.E_Commerce.Repository.RoleRepository;
import com.E_Commerce.E_Commerce.Repository.UserRepository;
import com.E_Commerce.E_Commerce.Service.AuthService;
import com.E_Commerce.E_Commerce.dto.LoginDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service; // <-- 1. IMPORT THIS

import java.util.HashSet;
import java.util.Set;

@Service // <-- 2. ADD THIS ANNOTATION
public class AuthServiceImpl implements AuthService {

    // 3. Declare dependencies as final
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    // 4. Create a constructor for dependency injection
    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String register(ResgisterDto resgisterDto) {
        // Now none of these will be null
        if(userRepository.existsByUsername(resgisterDto.getUsername())){
            return "Username already exists";
        }
        if(userRepository.existsByEmail(resgisterDto.getEmail())){
            return "Email already exists";
        }

        User user = new User();
        user.setFirstName(resgisterDto.getFirstName());
        user.setLastName(resgisterDto.getLastName());
        user.setUsername(resgisterDto.getUsername());
        user.setEmail(resgisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(resgisterDto.getPassword()));

        Set<Role> roles = new HashSet<>();

        // It's better to handle the case where a role might not exist
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
                 userRole = new Role();
                 userRole.setName("ROLE_USER");
                 roleRepository.save(userRole);
       }

        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }


    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User Logged in successfully";
    }
}

