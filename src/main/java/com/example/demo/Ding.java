package com.example.demo;

public class Ding {

    String id;
    String attribute;

    public Ding(String id, String attribute) {
        this.id = id;
        this.attribute = attribute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
