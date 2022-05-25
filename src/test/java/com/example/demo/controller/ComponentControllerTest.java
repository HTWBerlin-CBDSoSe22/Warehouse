package com.example.demo.controller;

import com.example.demo.controller.ComponentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ComponentControllerTest {

    @Autowired
    private ComponentController cController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(cController).isNotNull();
    }
}
