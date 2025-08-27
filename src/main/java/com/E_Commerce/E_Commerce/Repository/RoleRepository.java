package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
