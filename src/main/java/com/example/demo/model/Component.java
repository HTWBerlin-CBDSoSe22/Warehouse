package com.example.demo.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "component")

public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "component_id")
    private UUID id;

    @Column(name = "component_name")
    public String name;

    @Column(name = "component_price")
    public String price;

    @Column(name = "component_height")
    public String height;

    @Column(name = "component_weight")
    public String weight;

    @Column(name = "component_color")
    public String color;

    @Column(name = "component_countryOfOrigin")
    public String countryOfOrigin;

    @Column(name = "component_grade")
    public String grade;

    @Column(name = "component_category")
    public String category;

    @Column(name = "component_classification")
    public String classification;

    @Column(name = "component_harvestSeason")
    public String harvestSeason;

    public Component(UUID id, String name, String price, String height, String weight, String color, String countryOfOrigin, String grade, String category, String classification, String harvestSeason) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.height = height;
        this.weight = weight;
        this.color = color;
        this.countryOfOrigin = countryOfOrigin;
        this.grade = grade;
        this.category = category;
        this.classification = classification;
        this.harvestSeason = harvestSeason;
    }

    public Component() {

    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getHarvestSeason() {
        return harvestSeason;
    }

    public void setHarvestSeason(String harvestSeason) {
        this.harvestSeason = harvestSeason;
    }
}
