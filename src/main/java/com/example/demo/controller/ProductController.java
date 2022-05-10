package com.example.demo.controller;

import com.example.demo.model.Component;
import com.example.demo.model.Product;
import com.opencsv.CSVReader;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ProductController {
    private final String csvPathToProductsDev = "Products.csv";
    private final String csvPathToComponentsDev = "fruits.csv";

    @GetMapping(path = "/products")
    public List<Product> showAllComponents() {
        List<Product> listOfAllProducts;
        try {
            listOfAllProducts = readProductsFromCSV(csvPathToProductsDev);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "id not valid");
        }
        return listOfAllProducts;
    }
    @RequestMapping(value = "/products/{productId}", method=GET)
    public Product showSingleProduct(@PathVariable("productId") int productId){
        Product selectedSingleProduct;
        try {
            List<Product> listOfAllProducts = readProductsFromCSV(csvPathToProductsDev);
            selectedSingleProduct = listOfAllProducts.get(productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "id not valid");
        }
        return selectedSingleProduct;
    }

    /**
     * reads the products csv once and iterate through the lines
     *
     * @throws Exception
     */
    public List<Product> readProductsFromCSV(String csvPathToProductsDev) throws Exception
        {
            List<Product> productList;
            //Build reader instance
            CSVReader reader = new CSVReader(new FileReader(csvPathToProductsDev));
            //Read all rows at once
            List<String[]> allRows = reader.readAll();
            productList = getProductsFromCSVRows(allRows);
            return productList;
    }

    /**
     *
     * @param rowsOfProductCSV list of the rows from Products.CSV
     * @return
     */
    private List<Product> getProductsFromCSVRows(List<String[]> rowsOfProductCSV){
        List<Product> productList= new ArrayList<>();
        List<Component> componentsList = new ComponentController().importComponentDataFromCSV(csvPathToComponentsDev);
        for(String[] row : rowsOfProductCSV){

            // skip first row because there is no product defined
            if(row[0].equals("name"))
                continue;
            String[] componentsInProduct = row[1].split(" ");
            List<Component> listComponentsInProduct = new ArrayList<>();

            for(String s : componentsInProduct){
                s = s.trim();
                for(Component c : componentsList){
                    if(s.equals(c.name)){
                        listComponentsInProduct.add(c);
                    }
                }
            }
            productList.add(new Product(row[0], listComponentsInProduct));
        }
        return productList;
    }
}
