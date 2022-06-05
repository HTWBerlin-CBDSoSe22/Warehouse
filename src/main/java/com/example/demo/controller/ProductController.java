package com.example.demo.controller;

import com.example.demo.jpa.ProductRepository;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{productId}")
    Optional<Product> findProductById(@PathVariable("productId") long productId){
        return (productRepository.findById(productId));
    }

}
