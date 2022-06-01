package com.example.demo.controller;

import com.example.demo.jpa.ComponentRepository;
import com.example.demo.model.Component;
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
    ResponseEntity<Component> findComponentById(@PathVariable("componentId") long componentId) throws Exception {
        // boolean componentExists = componentRepository.existsById(componentId);
        // if (componentExists) {
        Optional<Component> newComponent = componentRepository.findById(componentId);
        if (newComponent.isEmpty()) { //true = null, kein objekt
            throw new Exception();
        } //sorry all over the place aber uebung ist vorbei und muss los
        return ResponseEntity.badRequest().build(); //hier passender response status
        //see zb. stackabuse.com how to return http status codes in a spring boot application
    }


}
