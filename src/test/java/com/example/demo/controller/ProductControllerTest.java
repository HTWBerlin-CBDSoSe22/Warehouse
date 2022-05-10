package com.example.demo.controller;

import com.example.demo.exception.WarehouseFileNotFoundException;
import com.example.demo.model.Component;
import com.example.demo.model.Product;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class ProductControllerTest {

    private final String componentCSVPathDev = "fruits.csv";
    private final String productCSVPathDev = "products.csv";
    private String componentCSVWrongPathDev = "vegetables.csv";
    private ProductController productController;

    @BeforeEach
    void setUp(){
        productController = new ProductController();
    }
    /**
     * checks whether the ProductController reads the two default products
     */
    @Test
    public void testProductControllerReadCSVGood01() {
        List<Product> listOfAllProducts= productController.readProductsFromCSV(productCSVPathDev);
        Assert.assertEquals("defaultProduct1",listOfAllProducts.get(0).name);
        Assert.assertEquals("defaultProduct2",listOfAllProducts.get(1).name);
    }
    /**
     * checks whether the default products have the defined amount of components
     */
    @Test
    public void testProductControllerReadCSVBad01() {
        List<Product> listOfAllProducts= productController.readProductsFromCSV(productCSVPathDev);
        Assert.assertEquals(5,listOfAllProducts.get(0).ingredients.size());
        Assert.assertEquals(4,listOfAllProducts.get(1).ingredients.size());
    }

    @Test
    public void testComponentControllerReadCSVBad01() {
        Exception exception = assertThrows(WarehouseFileNotFoundException.class, () -> productController.readProductsFromCSV(componentCSVWrongPathDev));
    }




}
