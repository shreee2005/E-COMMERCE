package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review , Long> {
    List<Review> findByProductId(Long productId);
}
