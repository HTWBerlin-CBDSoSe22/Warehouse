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

    /**
     * reads a csv file with OpenCSV
     * @param csvPathToProductsDev path to product ccv file
     * @return list of all products from csv file
     */
    public List<Product> readProductsFromCSV(String csvPathToProductsDev) throws CSVNullPointerException, WarehouseFileNotFoundException{
            List<Product> productList;
            try {
                CSVReader reader = new CSVReader(new FileReader(csvPathToProductsDev));
                // read all rows at once
                List<String[]> allRows = reader.readAll();
                productList = getProductsFromCSVRows(allRows);
            }catch (NullPointerException e) {
                throw new CSVNullPointerException("product csv file is empty ");
            } catch (FileNotFoundException e) {
                throw new WarehouseFileNotFoundException("product csv was not found");
            } catch (IOException | CsvException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while reading csv file");
            }
        return productList;
    }

    /**
     * goes through each row and converts these in product objects
     * @param rowsOfProductCSV list of the rows from Products.CSV
     */
    private List<Product> getProductsFromCSVRows(List<String[]> rowsOfProductCSV){
        List<Product> productList= new ArrayList<>();
        List<Component> componentsList = new ComponentController().importComponentDataFromCSV(csvPathToComponentsDev);
        for(String[] row : rowsOfProductCSV){
            // skip first row because there is no product defined
            if(row[0].equals("name"))
                continue;

            // separates all components that belong to a product
            String[] componentsInProduct = row[1].split(" ");
            List<Component> listComponentsInProduct = new ArrayList<>();

            // assigns the component names from the product row to a component object
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
