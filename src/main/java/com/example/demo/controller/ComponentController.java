package com.example.demo.controller;

import com.example.demo.model.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RestController
public class componentController {
   @GetMapping(path = "/components")
    public List<Component> getComponents(@RequestParam(value ="component_id", defaultValue = "-1") String component_id){
       List<Component> componentsList = new ArrayList<>();
       if(Integer.parseInt(component_id)==-1) {

       }

        return componentsList;
   }

}
