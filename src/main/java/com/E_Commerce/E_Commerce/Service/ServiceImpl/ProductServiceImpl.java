package com.E_Commerce.E_Commerce.Service.ServiceImpl;

import com.E_Commerce.E_Commerce.Model.Category;
import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Repository.CategoryRepository;
import com.E_Commerce.E_Commerce.Repository.ProductRepository;
import com.E_Commerce.E_Commerce.Repository.specification.ProductSpecification;
import com.E_Commerce.E_Commerce.Service.ProductService;
import com.E_Commerce.E_Commerce.dto.ProductDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    // Using constructor injection is the recommended modern practice
    public ProductServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    @Override
    public Optional<String> addProduct(ProductDto productDto) {
        // 1. Find the category by its ID
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + productDto.getCategoryId()));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDiscription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setStockQuantity(productDto.getStockQuantity());

        // 2. Set the fetched category on the new product
        product.setCategory(category);

        productRepository.save(product);
        return ("Product added successfully to category: " + category.getCategoryName()).describeConstable();
    }

    @Override
    public Page<Product> searchAndFilterProducts(String query, String category, Double minPrice, Double maxPrice, Pageable pageable) {
        Specification<Product> spec= ProductSpecification.findByCriteria(query , category , minPrice , maxPrice);
        return productRepository.findAll(spec , pageable);
    }

}