package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
