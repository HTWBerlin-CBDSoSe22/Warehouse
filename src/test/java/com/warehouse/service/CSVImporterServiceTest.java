package com.warehouse.service;

import com.warehouse.exception.WarehouseFileNotFoundException;
import com.warehouse.exception.WrongCSVHeaderException;
import com.warehouse.jpa.ComponentRepository;
import com.warehouse.jpa.ProductRepository;
import com.warehouse.model.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertThrows;

@SpringBootTest
public class CSVImporterServiceTest {

    private final String componentCSVGood = "Fruits.csv";
    private final String componentTestCSVGood = "testCSVs/testComponentCSVs/componentTestCSV.csv";
    private final String wrongComponentsCSV = "testCSVs/testComponentCSVs/wrongComponentsCSV.csv";
    private final String productTestCSVPath = "testCSVs/testProductCSVs/test01.csv";
    private final String productTestCSVPathNoHeader = "testCSVs/testProductCSVs/noHeader.csv";
    private final String wrongCSVPath = "vegetables.csv";

    @Autowired
    private ComponentRepository componentRepository;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.productRepository.deleteAll();
        this.componentRepository.deleteAll();
    }

    /** Component Tests **/
    @Test
    public void goodImportComponent() {
        Component expectedComponent = new Component("Banana", 0.75,
                13, 120, "yellow", "Ecuador",
                "H. extra", "dry", "Tropical fruit",
                "winter");
        CSVImporterService.importComponentsFromCSV(this.componentRepository, this.componentTestCSVGood);
        Component actualComponent = this.componentRepository.findAll().iterator().next();
        Assertions.assertEquals(expectedComponent.getName(), actualComponent.getName());
        Assertions.assertEquals(expectedComponent.getPrice(), actualComponent.getPrice());
        Assertions.assertEquals(expectedComponent.getHeight(), actualComponent.getHeight());
        Assertions.assertEquals(expectedComponent.getWeight(), actualComponent.getWeight());
        Assertions.assertEquals(expectedComponent.getColor(), actualComponent.getColor());
        Assertions.assertEquals(expectedComponent.getCountryOfOrigin(), actualComponent.getCountryOfOrigin());
        Assertions.assertEquals(expectedComponent.getGrade(), actualComponent.getGrade());
        Assertions.assertEquals(expectedComponent.getCategory(), actualComponent.getCategory());
        Assertions.assertEquals(expectedComponent.getClassification(), actualComponent.getClassification());
        Assertions.assertEquals(expectedComponent.getHarvestSeason(), actualComponent.getHarvestSeason());
    }

    @Test
    public void edgeComponentHasMissingProperties() {
        CSVImporterService.importComponentsFromCSV(this.componentRepository, this.wrongComponentsCSV);
        Assertions.assertEquals(0, this.componentRepository.count());
    }


    /** Product Tests **/
    @Test
    public void goodImportProduct() {
        CSVImporterService.importComponentsFromCSV(this.componentRepository, this.componentCSVGood);
        CSVImporterService.importProductsFromCSV(this.productRepository, this.productTestCSVPath);
        Assertions.assertEquals("defaultProduct1", this.productRepository.findAll().get(0).getName());
        Assertions.assertEquals(5, this.productRepository.findAll().get(0).getConsistsOf().size());
    }

    @Test
    public void edgeProductHasNoComponents() {
        CSVImporterService.importComponentsFromCSV(this.componentRepository, this.componentTestCSVGood);
        CSVImporterService.importProductsFromCSV(this.productRepository, this.productTestCSVPath);
        Assertions.assertEquals("defaultProduct1", this.productRepository.findAll().get(0).getName());
        Assertions.assertEquals(true, this.productRepository.findAll().get(1).getConsistsOf().isEmpty());
    }

    @Test
    public void edgeProductHasInvalidComponentsOrMoreCommas() {
        CSVImporterService.importComponentsFromCSV(this.componentRepository, this.componentCSVGood);
        CSVImporterService.importProductsFromCSV(this.productRepository, this.productTestCSVPath);
        Assertions.assertEquals("defaultProduct3", this.productRepository.findAll().get(2).getName());
        Assertions.assertEquals(true, this.productRepository.findAll().get(2).getConsistsOf().isEmpty());
    }

    @Test
    public void edgeProductHasMultipleComponentsWithSameName() {
        CSVImporterService.importComponentsFromCSV(this.componentRepository, this.componentCSVGood);
        String productTestPathWithWrongComponents = "testCSVs/testProductCSVs/multipleSameComponents.csv";
        CSVImporterService.importProductsFromCSV(this.productRepository, productTestPathWithWrongComponents);
        Assertions.assertEquals(5, this.productRepository.findAll().get(0).getConsistsOf().size());
    }

    @Test
    public void wrongFileNameGiven_shouldThrowWarehouseFileNotFoundException() {
        Exception exception = assertThrows(WarehouseFileNotFoundException.class,
                () -> CSVImporterService.importComponentsFromCSV(this.componentRepository, this.wrongCSVPath)
        );
    }

    @Test
    public void wrongProductHeader_shouldThrowWrongCSVHeaderException() {
        Exception exception = assertThrows(WrongCSVHeaderException.class,
                () -> CSVImporterService.importProductsFromCSV(this.productRepository, this.productTestCSVPathNoHeader)
        );
    }

}
