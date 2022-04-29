package com.example.demo.controller;

import com.example.demo.exception.CSVNullPointerException;
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
    private final String csvPathDev = "fruits.csv";

   @GetMapping(path = "/components")
    public List<Component> showAllComponents() {
       List<Component> listOfAllComponents;
       listOfAllComponents = importComponentDataFromCSV(csvPathDev);
       return listOfAllComponents;
   }

    @GetMapping(path = "/components/componentId")
    public Component showSingleComponent(@RequestParam int componentId){
        List<Component> listOfAllComponents;
        listOfAllComponents = importComponentDataFromCSV(csvPathDev);
        Component selectedSingleComponent = listOfAllComponents.get(componentId);
        if(checkComponentIdValidity(componentId) && selectedSingleComponent != null) {
            return selectedSingleComponent;
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
