package com.example.demo.controller;

import com.example.demo.jpa.ComponentRepository;
import com.example.demo.model.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/copmonents")
public class ComponentControllerJPA {

    @Autowired
    ComponentRepository componentRepository;

    @GetMapping
    List<Component> getComponents() {
        return (List<Component>) componentRepository.findAll();
    }

    @PostMapping
    Component createComponent(@RequestBody Component component) {
        return componentRepository.save(component);
    }


}
