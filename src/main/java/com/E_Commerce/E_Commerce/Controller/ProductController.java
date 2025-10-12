package com.E_Commerce.E_Commerce.Controller;

import com.E_Commerce.E_Commerce.Exceptions.InvalidProductToAdd;
import com.E_Commerce.E_Commerce.Exceptions.ProductNotFoundException;
import com.E_Commerce.E_Commerce.Model.Product;
import com.E_Commerce.E_Commerce.Service.ProductService;
import com.E_Commerce.E_Commerce.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
//    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String q ,
            @RequestParam(required = false) String category ,
            @RequestParam(required = false) Double minPrice ,
            @RequestParam(required = false) Double maxPrice ,
            Pageable pageable) {
        Page<Product> products =productService.searchAndFilterProducts(q , category , minPrice , maxPrice , pageable);
        return new ResponseEntity<>(products , HttpStatus.OK );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String > addProduct(@RequestBody ProductDto productDto) {
        String product1 = productService.addProduct(productDto).orElseThrow(()-> new InvalidProductToAdd("Invalid Product"));
        return new ResponseEntity<>(product1, HttpStatus.OK);
    }



}
