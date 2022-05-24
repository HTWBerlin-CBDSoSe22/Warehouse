
package com.example.demo.controller;

import com.example.demo.exception.WarehouseFileNotFoundException;
import com.example.demo.model.Component;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertThrows;

@SpringBootTest
public class ComponentControllerTest {

    private String componentCSVPathDev = "fruits.csv";
    private String componentCSVWrongPathDev = "vegetables.csv";
    private ComponentControllerOld componentController;

    @BeforeEach
void setUp(){
        componentController = new ComponentControllerOld();
    }



}
