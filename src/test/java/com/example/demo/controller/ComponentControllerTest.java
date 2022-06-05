package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.jpa.ComponentRepository;
import com.example.demo.model.Component;
import org.apache.catalina.filters.ExpiresFilter;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static sun.nio.cs.Surrogate.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import javax.servlet.ServletContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ComponentControllerTest {
//
//    ComponentRepository componentRepository;
//
//    @Bean
//    public CommandLineRunner commandlineRunner(ComponentRepository componentRepository) throws Exception {
//
//        return args -> {
//            componentRepository.deleteAll();
//            ArrayList<Component> components = new ArrayList<>();
//            components.add(new Component("Banana",0.75,
//                    13,120,"yellow","Ecuador",
//                    "H. extra","dry","Tropical fruit",
//                    "winter"));
//            components.add(new Component("Fruit 2",0.22,
//                    2,222,"green","Mosambique",
//                    "three","super wet","delicious",
//                    "autmn"));
//            componentRepository.saveAll(components);
//        };
//    }
//
////    @BeforeEach
////    void setUp(){
////        commandlineRunner();
////    }
//
//    @LocalServerPort
//    private int port;
//
//    TestRestTemplate restTemplate = new TestRestTemplate();
//
//    HttpHeaders headers = new HttpHeaders();
//
//    MockMvc mockMvc;
//    @Test
//    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
//        this.mockMvc.perform(get("/components")).andDo(print())
//                .andExpect(view().name("index"));
//    }
//
////    @Value("http://localhost:${local.server.port}")
////    String baseUrl;
////    @Test
////    public void testGet_returns_200_with_expected_employees() {
////        when().
////                get(baseUrl + "/components").
////                then()
////                .statusCode(200)
////                .body("size()", is(6))
////                .body("[0].firstName", equalTo("First"))
////                .body("[0].lastName", equalTo("Last"));
////    }
//
//    @Test
//    public void testWriteToDb_afterBoot_shouldHaveEntries(){
//        List<Component> all = (List<Component>) componentRepository.findAll();
//        Assertions.assertThat(all.size()).isEqualTo(6);
//        Assertions.assertThat(all.get(0).getName()).isEqualTo("First");
//        Assertions.assertThat(all.get(0).getName()).isEqualTo("Last");
//    }
//
//    @Test
//    public void testRetrieveComponents() throws JSONException {
//
//        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                createURLWithPort("/components"),
//                HttpMethod.GET, entity, String.class);
//
//        String expected = "{id:'Course1',name:'Spring',description:'10Steps'}";
//
//        JSONAssert.assertEquals(expected, response.getBody(), false);
//    }
//
//    private String createURLWithPort(String uri) {
//        return "http://localhost:" + port + uri;
//    }
//}

//@SpringBootTest(classes = ComponentController.class,
//        webEnvironment = WebEnvironment.RANDOM_PORT)
//public class ComponentControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Sql({"schema.sql", "data.sql"})
//    @Test
//    public void testAllEmployees() {
//        assertTrue(
//                this.restTemplate
//                        .getForObject("http://localhost:" + port + "/components", ComponentController.class)
//                        .getComponents().size() == 3);
//    }
//}

//
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@SpringBootTest
public class ComponentControllerTest {

//    ComponentRepository componentRepository;

//    @Value("http://localhost:${local.server.port}")
//    String baseUrl;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ComponentController cController;

    @Autowired
    private ComponentRepository componentRepository;

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

    @Test
    public void getComponentsRestApiTest() throws Exception {
//        String excpected = String.valueOf(componentRepository.findAll());
        String excpected = "{\"componentId\":1,\"name\":\"Banana\",\"price\":0.75,\"height\":13.0,\"weight\":120.0,\"color\":\"yellow\",\"countryOfOrigin\":\"Ecuador\",\"grade\":\"H. extra\",\"category\":\"dry\",\"classification\":\"Tropical fruit\",\"harvestSeason\":\"winter\"},{\"componentId\":2,\"name\":\"Apple\",\"price\":1.0,\"height\":7.0,\"weight\":200.0,\"color\":\"green\",\"countryOfOrigin\":\"Germany\",\"grade\":\"H. extra\",\"category\":\"dry\",\"classification\":\"Core\",\"harvestSeason\":\"fall\"},{\"componentId\":3,\"name\":\"Orange\",\"price\":1.2,\"height\":6.0,\"weight\":250.0,\"color\":\"orange\",\"countryOfOrigin\":\"Spain\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Citrus fruit\",\"harvestSeason\":\"summer\"},{\"componentId\":4,\"name\":\"Strawberry\",\"price\":0.15,\"height\":2.5,\"weight\":25.0,\"color\":\"red\",\"countryOfOrigin\":\"Spain\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"},{\"componentId\":5,\"name\":\"Blueberry\",\"price\":0.05,\"height\":0.8,\"weight\":5.0,\"color\":\"blue\",\"countryOfOrigin\":\"USA\",\"grade\":\"H. II\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"},{\"componentId\":6,\"name\":\"Mango\",\"price\":2.0,\"height\":12.0,\"weight\":700.0,\"color\":\"green\",\"countryOfOrigin\":\"India\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Tropical fruit\",\"harvestSeason\":\"summer\"},{\"componentId\":7,\"name\":\"Cherry\",\"price\":0.4,\"height\":1.5,\"weight\":20.0,\"color\":\"red\",\"countryOfOrigin\":\"Germany\",\"grade\":\"H. extra\",\"category\":\"fleshy\",\"classification\":\"Pit\",\"harvestSeason\":\"summer\"},{\"componentId\":8,\"name\":\"Pineapple\",\"price\":3.0,\"height\":25.0,\"weight\":1500.0,\"color\":\"yellow\",\"countryOfOrigin\":\"Ghana\",\"grade\":\"H. II\",\"category\":\"dry\",\"classification\":\"Tropical fruit\",\"harvestSeason\":\"spring\"},{\"componentId\":9,\"name\":\"Raspberry\",\"price\":0.35,\"height\":2.0,\"weight\":30.0,\"color\":\"red\",\"countryOfOrigin\":\"Poland\",\"grade\":\"H. extra\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"},{\"componentId\":10,\"name\":\"Grape\",\"price\":0.3,\"height\":1.0,\"weight\":25.0,\"color\":\"green\",\"countryOfOrigin\":\"USA\",\"grade\":\"H. I\",\"category\":\"fleshy\",\"classification\":\"Berry\",\"harvestSeason\":\"summer\"}]";
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/components",
                String.class)).contains(excpected);
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
//        String exp = String.valueOf(componentRepository.findAll());
        MvcResult mvcResult = this.mockMvc.perform(get("/components"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
//                .andExpect((ResultMatcher) jsonPath("$.message").value(exp))
//                .andReturn();

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

    //    @Test
//    void randomTest(){
//        assertThat(cController).
//    }

//    @Test
//    void someTest(){
//        List<Component> all = (List<Component>) componentRepository.findAll();
////        assertThat(all.size()).isEqualTo(10);
////        all.add(new Component());
////        all.remove(5);
////        all.remove(5);
////        all = (List<Component>) componentRepository.findAll();
//        assertThat(all.size()).isEqualTo(10);
//        assertThat(all.get(1).getName()).isEqualTo("Apple");
//        assertThat(all.get(1).getCategory()).isEqualTo("dry");
//    }

//    @Test
//    void someTest2(){
//        when().
//            get(baseUrl + ("/components")).
//        then().statusCode(200)
//            .body("size()", is(6))
//            .body("[0].firstName", equalTo("First"))
//            .body("[0].lastName", equalTo("Last"));
//    }

//    @Test
//    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
//        this.mockMvc.perform(get("/homePage")).andDo(print())
//                .andExpect(view().name("index"));
//    }


//    @Test
//    public void givenGreetURI_whenMockMVC_thenVerifyResponse() throws Exception {
//        MvcResult mvcResult = this.mockMvc.perform(get("/components"))
//                .andDo(print()).andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$.message").value("Hello World!!!"))
//                .andReturn();
//
////        Assert.assertEquals("application/json;charset=UTF-8",
////                mvcResult.getResponse().getContentType());
//    }
}
