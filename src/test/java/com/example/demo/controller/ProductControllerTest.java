package com.example.demo.controller;

import com.example.demo.exception.WarehouseFileNotFoundException;
import com.example.demo.model.Component;
import com.example.demo.model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;

public class ProductControllerTest {

    private final String componentCSVPathDev = "fruits.csv";
    private final String productCSVPathDev = "products.csv";
    private String componentCSVWrongPathDev = "vegetables.csv";
    private ProductController productController;
    private ComponentController componentController = new ComponentController();
    private List<Component> componentList= null;

    @BeforeEach
    void setUp(){

        productController = new ProductController();
        this.componentList = this.componentController.importComponentDataFromCSV(componentCSVPathDev);
    }
    /**
     * checks whether the ProductController reads the two default products
     */
    @Test
    public void testNameOfProducts() {
        List<Product> listOfAllProducts= productController.readProductsFromCSV(productCSVPathDev);

        Assert.assertEquals("defaultProduct1",listOfAllProducts.get(0).productName);
        Assert.assertEquals("defaultProduct2",listOfAllProducts.get(1).productName);
    }
    /**
     * checks whether the default products have the defined amount of components
     */
    @Test
    public void countNumberOfComponentsInProduct() {
        List<Product> listOfAllProducts= productController.readProductsFromCSV(productCSVPathDev);

        Assert.assertEquals(5,listOfAllProducts.get(0).ingredients.size());
        Assert.assertEquals(4,listOfAllProducts.get(1).ingredients.size());
    }
    @Test
    public void edgeProductHasNoComponents() {
        List<Product> listOfAllProducts= productController.readProductsFromCSV(productCSVPathDev);

        Assert.assertEquals(0,listOfAllProducts.get(4).ingredients.size());
    }

    @Test
    public void readComponentsFromString(){
        String[] componentsStrings = {"Cherry", "Banana", "Mango"};

        List<Component> componentsInProduct = productController.getComponentsOfProduct(componentsStrings, this.componentList);

        Assert.assertEquals(componentsInProduct.get(0).name,"Cherry");
        Assert.assertEquals(componentsInProduct.get(1).name,"Banana");
        Assert.assertEquals(componentsInProduct.get(2).name,"Mango");
    }

    @Test
    public void wrongFileNameGiven_shouldThrowWarehouseFileNotFoundException() {
        Exception exception = assertThrows(WarehouseFileNotFoundException.class,
                () -> productController.readProductsFromCSV(componentCSVWrongPathDev));
    }




}
