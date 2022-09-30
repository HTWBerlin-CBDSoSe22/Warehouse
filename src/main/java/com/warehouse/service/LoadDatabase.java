package com.warehouse.service;

import com.warehouse.exception.CSVNullPointerException;
import com.warehouse.exception.WarehouseFileNotFoundException;
import com.warehouse.exception.WrongCSVHeaderException;
import com.warehouse.jpa.ComponentRepository;
import com.warehouse.jpa.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.warehouse.service.CSVImporterService.importComponentsFromCSV;
import static com.warehouse.service.CSVImporterService.importProductsFromCSV;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ComponentRepository componentRepository, ProductRepository productRepository) {
        return args -> {
            try {
                importComponentsFromCSV(componentRepository, "Fruits.csv");
                importProductsFromCSV(productRepository, "Products.csv");
            } catch (WarehouseFileNotFoundException | CSVNullPointerException | WrongCSVHeaderException e) {
                System.err.print("error when importing csv data");
                e.printStackTrace();
            }
        };
    }
}
