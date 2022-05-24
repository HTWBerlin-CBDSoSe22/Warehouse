package com.example.demo.service;

import com.example.demo.exception.CSVNullPointerException;
import com.example.demo.exception.WarehouseFileNotFoundException;
import com.example.demo.jpa.ComponentRepository;
import com.example.demo.jpa.ProductRepository;
import com.example.demo.model.Component;
import com.example.demo.model.Product;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVImporterService {
    private static ComponentRepository componentRepositoryLoaded;
    private static ProductRepository productRepositoryLoaded;
    private static List<Component> componentList = null;

    public static void importComponentsFromCSV(ComponentRepository componentRepository, String path){
        List<Component> componentsFromCSV;
        try {
            componentsFromCSV = new CsvToBeanBuilder(new FileReader(path))
                    .withType(Component.class)
                    .build()
                    .parse();
        } catch (NullPointerException e) {
            throw new CSVNullPointerException("component csv file is empty ");
        } catch (FileNotFoundException e) {
            throw new WarehouseFileNotFoundException("component csv was not found");
        }
        for(Component c: componentsFromCSV){
            componentRepository.save(c);
        }
        componentRepositoryLoaded = componentRepository;
        componentList = componentsFromCSV;
    }

    public static void importProductsFromCSV(ProductRepository productRepository, String path){
        productRepositoryLoaded=productRepository;
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            List<String[]> allRowsOfCSVFile = reader.readAll();
            createProductsFromCSVRows(allRowsOfCSVFile);
        }catch (NullPointerException e) {
            throw new CSVNullPointerException("product csv file is empty ");
        } catch (FileNotFoundException e) {
            throw new WarehouseFileNotFoundException("product csv was not found");
        } catch (IOException | CsvException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while reading csv file");
        }
    }

    private static void createProductsFromCSVRows(List<String[]> rowsOfProductCSV){
        for(String[] row : rowsOfProductCSV){
            // skip product without component and skip fist csv row
            if(row.length==1 || row[0].equals("name"))
                continue;
            String[] componentsInProduct = row[1].split(" ");
            productRepositoryLoaded.save(new Product(row[0], getComponentsOfAProduct(componentsInProduct, componentList)));
        }
    }

    public static Set<Component> getComponentsOfAProduct(String[] componentsInProduct, List<Component> componentsList){
        Set<Component> listComponentsInProduct = new HashSet<>();
        for(String s : componentsInProduct){
            s = s.trim();
            for(Component c : componentsList){
                if(s.equals(c.getName())){
                    listComponentsInProduct.add(c);
                }
            }
        }
        return listComponentsInProduct;
    }


}
