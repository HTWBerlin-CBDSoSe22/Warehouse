package com.example.demo.controller;

import com.example.demo.model.Component;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertThrows;

@SpringBootTest
public class ComponentControllerTest {

    private String componentCSVPathDev = "fruits.csv";
    private String componentCSVWrongPathDev = "vegetables.csv";
    private ComponentController componentController;

    @BeforeEach
void setUp(){
        componentController=new ComponentController();
    }

    /**
     * checks whether the ComponentController reads 10 components from the fruits.csv
     */
    @Test
    public void testComponentControllerReadCSVGood01() throws FileNotFoundException {
        List<Component> components= componentController.importComponentDataFromCSV(componentCSVPathDev);
        Assert.assertEquals(components.size(),10);


    }

    /**
     * first component from list has to be banana
     * last component has to be grape
     */
    @Test
    public void testComponentControllerReadCSVGood02() throws FileNotFoundException {
        List<Component> components= componentController.importComponentDataFromCSV(componentCSVPathDev);
        Assert.assertEquals(components.get(0).name,"Banana");
        Assert.assertEquals(components.get(9).name,"Grape");
    }

    /**
     * checks properties from certain components in the list
     */
    @Test
    public void testComponentControllerReadCSVGood03() throws FileNotFoundException {
        List<Component> components= componentController.importComponentDataFromCSV(componentCSVPathDev);
        Assert.assertEquals(components.get(5).name,"Mango");
        Assert.assertEquals(components.get(5).countryOfOrigin,"India");
    }

    /**
     * test if exception is thrown, when file was not found
     * @throws FileNotFoundException when path is incorrect or file does not exist
     */
    @Test
    public void testComponentControllerReadCSVBad01() throws FileNotFoundException {
        Exception exception = assertThrows(FileNotFoundException.class, () -> componentController.importComponentDataFromCSV(componentCSVWrongPathDev));
    }

}
