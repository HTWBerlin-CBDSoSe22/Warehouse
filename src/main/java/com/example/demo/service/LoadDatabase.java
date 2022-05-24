package com.example.demo.service;

import com.example.demo.jpa.ComponentRepository;
import com.example.demo.jpa.ProductRepository;
import com.example.demo.model.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.example.demo.service.CSVImporterService.importComponentsFromCSV;
import static com.example.demo.service.CSVImporterService.importProductsFromCSV;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ComponentRepository componentRepository, ProductRepository productRepository) {

        //String name, double price, double height, double weight, String color, String countryOfOrigin, String grade, String category, String classification, String harvestSeason) {
        return args -> {
            importComponentsFromCSV(componentRepository, "Fruits.csv");
            importProductsFromCSV(productRepository, "Products.csv");
        };
    }
}
