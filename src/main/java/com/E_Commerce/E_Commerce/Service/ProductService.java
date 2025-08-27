package com.E_Commerce.E_Commerce.Service;

import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Repository.CategoryRepository;
import com.E_Commerce.E_Commerce.Repository.ProductRepository;
import com.E_Commerce.E_Commerce.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public List<Product> getAllProducts();

    public Product getProductById(Long id);

    public String addProduct(ProductDto productDto);

}
