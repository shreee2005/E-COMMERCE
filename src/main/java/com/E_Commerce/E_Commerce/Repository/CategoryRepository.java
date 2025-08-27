package com.E_Commerce.E_Commerce.Repository;

import com.E_Commerce.E_Commerce.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
