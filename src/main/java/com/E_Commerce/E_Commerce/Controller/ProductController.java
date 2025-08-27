package com.E_Commerce.E_Commerce.Controller;

import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Service.ProductService;
import com.E_Commerce.E_Commerce.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products , HttpStatus.OK );
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String > addProduct(@RequestBody ProductDto productDto) {
        String product1 = productService.addProduct(productDto);
        return new ResponseEntity<>(product1, HttpStatus.OK);
    }


}
