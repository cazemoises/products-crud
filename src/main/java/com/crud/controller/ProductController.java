package com.crud.controller;

import java.time.LocalDate;

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
            String productId = productStorage.store(productRequest.getName(), productRequest.getDescription(), productRequest.getPrice(), productRequest.getQuantity(), LocalDate.now(), null);
            return ResponseEntity.ok("Product stored with ID: " + productId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error storing product");
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Product> find(@PathVariable String id) {
        try {
            Product product = productStorage.find(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Product());
        }
    }

    @GetMapping("/")
    ResponseEntity<Product[]> list() {
        try {
            Product[] products = productStorage.list();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new Product[0]);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<String> update(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        try {
            productStorage.update(id, productRequest.getName(), productRequest.getDescription(), productRequest.getPrice(), productRequest.getQuantity(), LocalDate.now());
            return ResponseEntity.ok("Product updated with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating product");
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable String id) {
        try {
            productStorage.delete(id);
            return ResponseEntity.ok("Product deleted with ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting product");
        }
    }

}