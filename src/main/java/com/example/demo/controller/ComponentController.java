package com.example.demo.controller;

import com.example.demo.model.Component;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@RestController
public class ComponentController {

    private String csvPathDev = "fruits.csv";

    /**
     * get endpoint which responds with all hardware components in a list in JSON format
     * @return a list of all components from the fruits.csv file
     */
   @GetMapping(path = "/components")
    public List<Component> getAllComponents() throws FileNotFoundException {
       List<Component> componentsList;
       componentsList = importComponentDataFromCSV(csvPathDev);
        return componentsList;
   }

    /**
     * get endpoint which responds with a single component in JSON format
     * @param componentId the specified component -> integer between 0 and 9
     * @return a specific hardware component
     */
    @GetMapping(path = "/components/componentId")
    public Component getSingleComponent(@RequestParam int componentId) throws ResponseStatusException{
        List<Component> componentsList = null;
        try {
            componentsList = importComponentDataFromCSV(csvPathDev);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(componentId >= 0 && componentId <= 9) {
            return componentsList.get(componentId);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id not valid");
        }
    }
    public List<Component> importComponentDataFromCSV(String pathToCSV) throws FileNotFoundException{
        List<Component> loadedComponentList=new CsvToBeanBuilder(new FileReader(pathToCSV))
                    .withType(Component.class)
                    .build()
                    .parse();
       return loadedComponentList;
    }
}
