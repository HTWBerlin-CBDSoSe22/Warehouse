package com.example.demo.jpa;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
/*
    Product findByProductId(long productId);
    List<Product> findByName(String name);
    List<Product> findAll();

 */

}
