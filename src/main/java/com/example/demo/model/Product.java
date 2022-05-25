package com.example.demo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="products_components",
            joinColumns = @JoinColumn(name="product_id"),
            inverseJoinColumns = @JoinColumn(name="component_id")
    )
    private Set<Component> consistsOf = new HashSet<Component>();

    @Column(name = "name")
    public String name;

    public Product(String name, Set<Component> components) {
        this.name = name;
        this.consistsOf = components;
    }

    public Product() { }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                '}';
    }

    public Set<Component> getConsistsOf() {
        return consistsOf;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addComponent(Component component) {
        consistsOf.add(component);
    }
}
