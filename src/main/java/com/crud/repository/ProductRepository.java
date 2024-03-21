package com.crud.repository;

import com.crud.model.Product;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {
    List<Product> list();
    void store(Product product);
    void update(Product product);
    void delete(int id);
    Product listOne(int id);
}