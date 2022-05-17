package com.example.demo.jpa;

import com.example.demo.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findByProductId(long productId);
    List<Product> findByName(String name);
}
