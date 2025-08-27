package com.E_Commerce.E_Commerce.Service;

import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Repository.CategoryRepository;
import com.E_Commerce.E_Commerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).get();
    }
}
