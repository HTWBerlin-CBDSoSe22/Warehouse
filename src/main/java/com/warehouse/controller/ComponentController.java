package com.warehouse.controller;

import com.warehouse.jpa.ComponentRepository;
import com.warehouse.model.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/components")
public class ComponentController {

    @Autowired
    ComponentRepository componentRepository;

    @GetMapping
    List<Component> getComponents() {
        return (List<Component>) componentRepository.findAll();
    }

    @GetMapping(path = "/{componentId}")
    ResponseEntity<Component> findComponentById(@PathVariable("componentId") long componentId) {
        Optional<Component> searchedComponent = componentRepository.findById(componentId);
        if (searchedComponent.isEmpty()) { //true = null, kein objekt
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(searchedComponent.get());
    }

}
