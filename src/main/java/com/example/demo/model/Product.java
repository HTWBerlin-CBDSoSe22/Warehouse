package com.example.demo.model;

import java.util.List;

public class Product {
    public String name;
    public List<Component> ingredients;

    public Product(String name, List<Component> ingredients){
        this.name = name;
        this.ingredients = ingredients;
    }
}
