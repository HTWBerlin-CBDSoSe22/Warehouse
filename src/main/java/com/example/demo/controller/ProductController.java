package com.example.demo.controller;

import com.example.demo.jpa.ComponentRepository;
import com.example.demo.jpa.ProductRepository;
import com.example.demo.model.Component;
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

    @Autowired
    ComponentRepository componentRepository;

    @GetMapping
    List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{productId}/components/{componentId}")
    Product addComponentToProduct(
            @PathVariable Long productId,
            @PathVariable Long componentId
    ) {
        Product product = productRepository.findById(productId).get();
        Component component = componentRepository.findById(componentId).get();
        product.addComponent(component);
        return productRepository.save(product);
    }
}
