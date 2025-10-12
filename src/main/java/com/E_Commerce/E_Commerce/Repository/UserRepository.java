package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// No @Repository annotation needed on interfaces
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    // Method name must match the field name: 'Email'
    Boolean existsByEmail(String Email);

    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<User> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail, String orEmail);
}
