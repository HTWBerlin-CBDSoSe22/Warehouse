package com.warehouse.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductController pController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void contextLoads() {
        assertThat(pController).isNotNull();
    }

    @Test
    public void getProductsStatusIsOkAndContentTypeIsJsonTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/products"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assert.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void getProductsContainsProductIdAndComponentIdAndClassificationTest() throws Exception {
        this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("productId")))
                .andExpect(content().string(containsString("componentId")))
                .andExpect(content().string(containsString("classification")));
    }

    @Test
    public void findProductByIdFirstIdStatusIsOkTest() throws Exception {
        this.mockMvc.perform(get("/products/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findProductByIdLastIdStatusIsOkTest() throws Exception {
        this.mockMvc.perform(get("/products/5")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findProductByIdNotExistingIdStatusNotFoundTest() throws Exception {
        this.mockMvc.perform(get("/products/6")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void findProductByIdLastIdStatusIsOkAndContentTypeIsJsonTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/products/5"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assert.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void findProductByIdFirstIdContainsProductIdTest() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/products/1",
                String.class)).contains("productId");
    }

    @Test
    public void findProductByIdFirstIdContainsProductIdAndAppleAndCherryTest() throws Exception {
        this.mockMvc.perform(get("/products/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("productId")))
                .andExpect(content().string(containsString("Apple")))
                .andExpect(content().string(containsString("Cherry")));
    }

}
