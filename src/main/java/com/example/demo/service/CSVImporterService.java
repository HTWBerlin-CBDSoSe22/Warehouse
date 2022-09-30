package com.example.demo.service;

import com.example.demo.exception.CSVNullPointerException;
import com.example.demo.exception.WarehouseFileNotFoundException;
import com.example.demo.exception.WrongCSVHeaderException;
import com.example.demo.jpa.ComponentRepository;
import com.example.demo.jpa.ProductRepository;
import com.example.demo.model.Component;
import com.example.demo.model.Product;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVImporterService {
    private static ComponentRepository componentRepositoryLoaded;
    private static ProductRepository productRepositoryLoaded;
    private static List<Component> componentList = null;

    /**
     * converts each csv row to a component object and adds them to a list
     */
    public static void importComponentsFromCSV(ComponentRepository componentRepository, String path) {
        List<Component> componentsFromCSV = null;
        try {
            componentsFromCSV = new CsvToBeanBuilder(new FileReader(path))
                    .withType(Component.class)
                    .build()
                    .parse();
        } catch (NullPointerException e) {
            throw new CSVNullPointerException("component csv file is empty ");
        } catch (FileNotFoundException e) {
            throw new WarehouseFileNotFoundException("component csv was not found");
        } catch (RuntimeException e) {
            System.err.print("missing properties of component");
            e.printStackTrace();
        }
        if (componentsFromCSV == null)
            return;
        for (Component c : componentsFromCSV) {
            componentRepository.save(c);
        }
        componentRepositoryLoaded = componentRepository;
        componentList = componentsFromCSV;
    }

    public static void importProductsFromCSV(ProductRepository productRepository, String path) throws WarehouseFileNotFoundException, CSVNullPointerException {
        productRepositoryLoaded = productRepository;
        try {
            CSVReader reader = new CSVReader(new FileReader(path));
            List<String[]> allRowsOfCSVFile = reader.readAll();
            createProductsFromCSVRows(allRowsOfCSVFile);
        } catch (NullPointerException e) {
            throw new CSVNullPointerException("product csv file is empty ");
        } catch (FileNotFoundException e) {
            throw new WarehouseFileNotFoundException("product csv was not found");
        } catch (IOException | CsvException e) {
            System.out.print("IOException or CsvException when importing csv data");
            e.printStackTrace();
        }
    }

    private static void createProductsFromCSVRows(List<String[]> rowsOfProductCSV) throws WrongCSVHeaderException {
        String[] headerRow = rowsOfProductCSV.get(0);
        if (checkForValidCSVHeader(headerRow))
            throw new WrongCSVHeaderException("csv header for products is wrong");

        rowsOfProductCSV.remove(0);
        for (String[] row : rowsOfProductCSV) {
            //
            if (row.length == 1)
                continue;
            String[] componentsInProduct = row[1].split(" ");
            productRepositoryLoaded.save(new Product(row[0], getComponentsOfAProduct(componentsInProduct, componentList)));
        }
    }

    private static Set<Component> getComponentsOfAProduct(String[] componentsInProduct, List<Component> componentsList) {
        Set<Component> listComponentsInProduct = new HashSet<>();
        for (String s : componentsInProduct) {
            s = s.trim();
            for (Component c : componentsList) {
                if (s.equals(c.getName())) {
                    listComponentsInProduct.add(c);
                }
            }
        }
        return listComponentsInProduct;
    }

    private static boolean checkForValidCSVHeader(String[] stringArray) {
        return (!(stringArray.length == 2 && stringArray[0].trim().equals("name") && stringArray[1].trim().equals("ingredients")));
    }

}
