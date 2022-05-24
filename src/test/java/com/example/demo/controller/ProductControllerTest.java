
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







}
