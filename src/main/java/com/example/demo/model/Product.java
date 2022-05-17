package com.example.demo.model;
import java.util.List;


public class Product {

    public String productName;
    public List<Component> ingredients;

    public Product(String name, List<Component> ingredients){
        this.productName = name;
        this.ingredients = ingredients;
    }

}
