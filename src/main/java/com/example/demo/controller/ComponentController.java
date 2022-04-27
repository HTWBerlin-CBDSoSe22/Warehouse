package com.example.demo.controller;

import com.example.demo.model.Component;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ComponentController {

    private int id;
    private String csvPathDev = "fruits.csv";

    /**
     * get endpoint which responds with all hardware components in a list in JSON format
     * @return a list of all components from the fruits.csv file
     */
   @GetMapping(path = "/components")
    public List<Component> getComponents(){
       List<Component> componentsList;
       componentsList = readComponentCSV(csvPathDev);
        return componentsList;
   }

    /**
     * get endpoint which responds with a single component in JSON format
     * @param id the specified component -> integer between 0 and 9
     * @return a specific hardware component
     */
    @GetMapping(path = "/components/componentId")
    public Component getSingleComponent(@RequestParam int id){
        List<Component> componentsList = readComponentCSV(csvPathDev);
        if(id > -1 && id < 10) {
            return componentsList.get(id);
        }else{
            return componentsList.get(id);
        }
    }

    public List<Component> readComponentCSV(String path) {
       List<Component> loadedComponentList= new ArrayList<>();
        try {
            loadedComponentList = new CsvToBeanBuilder(new FileReader(path))
                    .withType(Component.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       return loadedComponentList;
    }

}
