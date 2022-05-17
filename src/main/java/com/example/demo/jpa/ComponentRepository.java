package com.example.demo.jpa;

import java.util.List;
import java.util.UUID;

import com.example.demo.model.Component;
import org.springframework.data.repository.CrudRepository;

public interface ComponentRepository extends CrudRepository<Component, UUID> {

    Component findById(long id);
    List<Component> findByPrice(double price);
    List<Component> findByHeight(double height);
    List<Component> findByWeight(double weight);
    List<Component> findByColor(String color);
    List<Component> findByCountryOfOrigin(String country);
    List<Component> findByClassification(String classification);
    List<Component> findByHarvestSeason(String harvestSeason);



}
