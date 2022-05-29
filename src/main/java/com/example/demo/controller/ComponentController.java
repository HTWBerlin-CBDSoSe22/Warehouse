package com.example.demo.controller;

import com.example.demo.jpa.ComponentRepository;
import com.example.demo.model.Component;
import org.springframework.beans.factory.annotation.Autowired;
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
    Optional<Component> findComponentById(@PathVariable("componentId") long componentId){
        return (componentRepository.findById(componentId));
    }

}
