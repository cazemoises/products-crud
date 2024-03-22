package com.crud.controller;

import com.crud.storage.ProductStorage;
import com.crud.dto.ProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    private ProductStorage productStorage;

    public ProductController() {
        try {
            this.productStorage = new ProductStorage();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize ProductStorage", e);
        }
    }
    
    @PostMapping("/store")
    ResponseEntity<String> store(@RequestBody ProductRequest productRequest) {
        try {
            String productId = productStorage.store(productRequest.getName(), productRequest.getDescription(), productRequest.getPrice());
            return ResponseEntity.ok("Product stored with ID: " + productId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error storing product");
        }
    }

}