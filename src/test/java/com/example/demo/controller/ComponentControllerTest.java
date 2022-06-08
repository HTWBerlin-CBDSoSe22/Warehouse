package com.example.demo.controller;

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
import javax.servlet.ServletContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ComponentControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ComponentController cController;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(cController).isNotNull();
    }

    // getComponents() Tests
    @Test
    public void getComponentsRestApiTest() throws Exception {
        String expected = "{\"componentId\":1,\"name\":\"Banana\",\"price\":0.75,\"height\":13.0,\"weight\":120.0,\"color\":\"yellow\",\"countryOfOrigin\":\"Ecuador\",\"grade\":\"H. extra\",\"category\":\"dry\",\"classification\":\"Tropical fruit\",\"harvestSeason\":\"winter\"},{\"componentId\":2,\"name\":\"Apple\",\"price\":1.0,\"height\":7.0,\"weight\":200.0,\"color\":\"green\",\"countryOfOrigin\":\"Germany\",\"grade\":\"H. extra\",\"category\":\"dry\",\"classification\":\"Core\",\"harvestSeason\":\"fall\"},{\"componentId\":3,\"name\":\"Orange\",\"price\":1.2,\"height\":6.0,\"weight\":250.0,\"color\":\"orange\",\"countryOfOrigin\":\"Spain\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Citrus fruit\",\"harvestSeason\":\"summer\"},{\"componentId\":4,\"name\":\"Strawberry\",\"price\":0.15,\"height\":2.5,\"weight\":25.0,\"color\":\"red\",\"countryOfOrigin\":\"Spain\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"},{\"componentId\":5,\"name\":\"Blueberry\",\"price\":0.05,\"height\":0.8,\"weight\":5.0,\"color\":\"blue\",\"countryOfOrigin\":\"USA\",\"grade\":\"H. II\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"},{\"componentId\":6,\"name\":\"Mango\",\"price\":2.0,\"height\":12.0,\"weight\":700.0,\"color\":\"green\",\"countryOfOrigin\":\"India\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Tropical fruit\",\"harvestSeason\":\"summer\"},{\"componentId\":7,\"name\":\"Cherry\",\"price\":0.4,\"height\":1.5,\"weight\":20.0,\"color\":\"red\",\"countryOfOrigin\":\"Germany\",\"grade\":\"H. extra\",\"category\":\"fleshy\",\"classification\":\"Pit\",\"harvestSeason\":\"summer\"},{\"componentId\":8,\"name\":\"Pineapple\",\"price\":3.0,\"height\":25.0,\"weight\":1500.0,\"color\":\"yellow\",\"countryOfOrigin\":\"Ghana\",\"grade\":\"H. II\",\"category\":\"dry\",\"classification\":\"Tropical fruit\",\"harvestSeason\":\"spring\"},{\"componentId\":9,\"name\":\"Raspberry\",\"price\":0.35,\"height\":2.0,\"weight\":30.0,\"color\":\"red\",\"countryOfOrigin\":\"Poland\",\"grade\":\"H. extra\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"},{\"componentId\":10,\"name\":\"Grape\",\"price\":0.3,\"height\":1.0,\"weight\":25.0,\"color\":\"green\",\"countryOfOrigin\":\"USA\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"}]";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/components",
                String.class)).contains(expected);
    }

    @Test
    public void componentsStatusIsOkTest() throws Exception {
        this.mockMvc.perform(get("/components")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void servletOntextIsNotNullAndComponentsControllerBeanExistsTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof ServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("componentController"));
    }

    @Test
    public void statusIsOkComponentsTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/components"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assert.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }
    @Test
    public void nameAppleExistsInComponentsTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/components",
                String.class)).contains("Apple");
    }

    @Test
    public void countriesGermanyEcuadorSpainExistsInComponentsTest() throws Exception {
        this.mockMvc.perform(get("/components")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Germany")))
                .andExpect(content().string(containsString("Ecuador")))
                .andExpect(content().string(containsString("Spain")));
    }

    // findComponentById() Tests

    @Test
    public void findComponentByIdFirstIdStatusIsOkTest() throws Exception {
        this.mockMvc.perform(get("/components/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findComponentByIdLastIdStatusIsOkTest() throws Exception {
//        String sizeComponentsAll = String.valueOf(componentRepository.findAll().size());
//        this.mockMvc.perform(get("/components/" + sizeComponentsAll)).andDo(print())
//                .andExpect(status().isOk());
        this.mockMvc.perform(get("/components/10")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findComponentByIdNotExistingIdStatusNotFoundTest() throws Exception {
        this.mockMvc.perform(get("/components/11")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void findComponentByIdLastIdStatusIsOkAndContentTypeIsJsonTest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/components/10"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assert.assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void findComponentByIdFirstIdContainsBananaTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/components/1",
                String.class)).contains("Banana");
    }

    @Test
    public void findComponentsByIdFirstIdContainsBananaAndEcuadorAndWinterTest() throws Exception {
        this.mockMvc.perform(get("/components/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Banana")))
                .andExpect(content().string(containsString("Ecuador")))
                .andExpect(content().string(containsString("winter")));
    }

}
