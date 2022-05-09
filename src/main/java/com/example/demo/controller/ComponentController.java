package com.example.demo.controller;

import com.example.demo.exception.CSVNullPointerException;
import com.example.demo.model.Component;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ComponentController {
    private final String csvPathDev = "fruits.csv";

   @GetMapping(path = "/components")
    public List<Component> showAllComponents() {
       List<Component> listOfAllComponents;
       listOfAllComponents = importComponentDataFromCSV(csvPathDev);
       return listOfAllComponents;
   }

    @RequestMapping(value = "/components/{componentId}", method=GET)
    public Component showSingleComponent(@PathVariable("componentId") int componentId){
        List<Component> listOfAllComponents;
        listOfAllComponents = importComponentDataFromCSV(csvPathDev);
        Component selectedSingleComponent;
        if(checkComponentIdValidity(componentId)) {
            if((selectedSingleComponent=listOfAllComponents.get(componentId))!=null)
                return selectedSingleComponent;
            else
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "id empty");
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id not valid");
        }
    }

    /**
     * converts each csv row to a component object and adds them to a list
     * @param pathToCSV
     * @return list of all components from csv file
     * @throws CSVNullPointerException csv file is empty
     */
    public List<Component> importComponentDataFromCSV(String pathToCSV) throws CSVNullPointerException {
        List<Component> componentsFromCSV = null;
        try {
            componentsFromCSV = new CsvToBeanBuilder(new FileReader(pathToCSV))
                        .withType(Component.class)
                        .build()
                        .parse();
        } catch (NullPointerException e) {
            throw new CSVNullPointerException("component csv file is empty ");
        } catch (FileNotFoundException e) {
            // todo: throw and define warehouseFileNotFoundException and exception for reading access denied
        }
        return componentsFromCSV;
    }

    private boolean checkComponentIdValidity(int componentId){
       int maxComponentId = 9;
       int minComponentId = 0;
        return minComponentId <= componentId && componentId <= maxComponentId;
    }

}
