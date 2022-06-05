package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private ProductController pController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(pController).isNotNull();
    }
}