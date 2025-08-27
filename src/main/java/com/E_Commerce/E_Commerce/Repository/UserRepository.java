package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// No @Repository annotation needed on interfaces
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    // Method name must match the field name: 'Email'
    Boolean existsByEmail(String Email);

    Optional<User> findByUsernameOrEmail(String username, String email);

}
