package com.example.demo.model;


public class Component {

    public String name;
    public String price;
    public String height;
    public String weight;
    public String color;
    public String countryOfOrigin;
    public String grade;
    public String category;
    public String classification;
    public String harvestSeason;

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", color='" + color + '\'' +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", grade='" + grade + '\'' +
                ", category='" + category + '\'' +
                ", classification='" + classification + '\'' +
                ", harvestSeason='" + harvestSeason + '\'' +
                '}';
    }

}
