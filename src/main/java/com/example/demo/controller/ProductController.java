package com.example.demo.controller;

import com.example.demo.exception.CSVNullPointerException;
import com.example.demo.exception.WarehouseFileNotFoundException;
import com.example.demo.model.Component;
import com.example.demo.model.Product;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ProductController {
    private final String csvPathToProductsDev = "Products.csv";
    private final String csvPathToComponentsDev = "fruits.csv";
    private ComponentController componentController = new ComponentController();

    @GetMapping(path = "/products")
    public List<Product> showAllComponents() {
        List<Product> listOfAllProducts;
            listOfAllProducts = readProductsFromCSV(csvPathToProductsDev);
        return listOfAllProducts;
    }
    @RequestMapping(value = "/products/{productId}", method=GET)
    public Product showSingleProduct(@PathVariable("productId") int productId){
        Product selectedSingleProduct;
        List<Product> listOfAllProducts = readProductsFromCSV(csvPathToProductsDev);
        if(0 > productId || productId >= listOfAllProducts.size()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        selectedSingleProduct=listOfAllProducts.get(productId);
        return selectedSingleProduct;
    }

    public List<Product> readProductsFromCSV(String csvPathToProductsDev) throws CSVNullPointerException, WarehouseFileNotFoundException{
            List<Product> listOfAllProducts = new ArrayList<>();
            try {
                CSVReader reader = new CSVReader(new FileReader(csvPathToProductsDev));
                List<String[]> allRowsOfCSVFile = reader.readAll();
                listOfAllProducts = createProductsFromCSVRows(listOfAllProducts, allRowsOfCSVFile);
            }catch (NullPointerException e) {
                throw new CSVNullPointerException("product csv file is empty ");
            } catch (FileNotFoundException e) {
                throw new WarehouseFileNotFoundException("product csv was not found");
            } catch (IOException | CsvException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while reading csv file");
            }
        return listOfAllProducts;
    }


     // goes through each row of the read csv file and converts each in product objects
    private List<Product> createProductsFromCSVRows(List<Product> listOfAllProducts, List<String[]> rowsOfProductCSV){
        List<Component> componentsList = componentController.importComponentDataFromCSV(csvPathToComponentsDev);
        for(String[] row : rowsOfProductCSV){
            // product without component and skip fist csv row
            if(row.length==1 || row[0].equals("name"))
                continue;
            String[] componentsInProduct = row[1].split(" ");
            listOfAllProducts.add(new Product(row[0], getComponentsOfProduct(componentsInProduct, componentsList)));
        }
        return listOfAllProducts;
    }

    public List<Component> getComponentsOfProduct(String[] componentsInProduct, List<Component> componentsList){
        List<Component> listComponentsInProduct = new ArrayList<>();
        for(String s : componentsInProduct){
            s = s.trim();
            for(Component c : componentsList){
                if(s.equals(c.name)){
                    listComponentsInProduct.add(c);
                }
            }
        }
        return listComponentsInProduct;
    }
}
