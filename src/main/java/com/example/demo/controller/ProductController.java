package com.example.demo.controller;

import com.example.demo.jpa.ComponentRepository;
import com.example.demo.jpa.ProductRepository;
import com.example.demo.model.Component;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ComponentRepository componentRepository;

    @GetMapping
    List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping(path = "/{productId}")
    ResponseEntity<Product> findProductById(@PathVariable("productId") long productId){
        Optional<Product> searchedProduct = productRepository.findById(productId);
        if (searchedProduct.isEmpty()) { //true = null, kein objekt
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(searchedProduct.get());
    }

}
