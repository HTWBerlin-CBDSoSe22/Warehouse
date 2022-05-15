package com.example.demo.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Product {

    @Id
    private UUID id;
    public String name;


    public Product(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}