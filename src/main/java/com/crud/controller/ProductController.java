package com.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crud.dto.ProductRequest;
import com.crud.model.Product;
import com.crud.storage.ProductStorage;

@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private final ProductStorage productStorage;

    public ProductController() {
        this.productStorage = new ProductStorage();
    }
    
    @PostMapping("/store")
    ResponseEntity<String> store(@RequestBody ProductRequest productRequest) {
        try {
            String productId = productStorage.store(productRequest.getId(), productRequest.getName(), productRequest.getDescription(), productRequest.getPrice());
            return ResponseEntity.ok("Product stored with ID: " + productId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error storing product");
        }
    }

    @GetMapping("/")
    ResponseEntity<Product[]> list() {
        try {
            System.out.println("TESTE");
            Product[] products = productStorage.list();
            System.out.println("TESTE");
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Product[0]);
        }
    }

}