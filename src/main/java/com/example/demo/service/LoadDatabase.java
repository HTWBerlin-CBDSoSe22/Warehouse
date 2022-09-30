package com.example.demo.service;

import com.example.demo.exception.CSVNullPointerException;
import com.example.demo.exception.WarehouseFileNotFoundException;
import com.example.demo.exception.WrongCSVHeaderException;
import com.example.demo.jpa.ComponentRepository;
import com.example.demo.jpa.ProductRepository;
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
        return args -> {
            try {
                importComponentsFromCSV(componentRepository, "Fruits.csv");
                importProductsFromCSV(productRepository, "Products.csv");
            }catch(WarehouseFileNotFoundException | CSVNullPointerException | WrongCSVHeaderException e){
                System.err.print("error when importing csv data");
                e.printStackTrace();
            }
        };
    }
}
