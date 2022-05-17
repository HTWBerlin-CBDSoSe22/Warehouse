package com.example.demo;

import com.example.demo.jpa.ComponentRepository;
import com.example.demo.model.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2

public class DemoApplication {

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ComponentRepository componentRepo) {
		return (args) -> {
			// save a few customers
			componentRepo.save(new Component("Banana", 0.75, 13, 120, "yellow", "Ecuador" , "H. extra", "dry", "Tropical fruit", "winter"));
			componentRepo.save(new Component("Strawberry", 0.15, 2.5, 25, "red", "Spain", "H. I", "fleshy", "Berry", "summer"));
			componentRepo.save(new Component("Grape", 0.3, 1, 25, "green", "USA", "H. I", "fleshy", "Berry", "summer"));


			// fetch all customers
			log.info("All components:");
			for (Component component : componentRepo.findAll()) {
				log.info(component.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			/*
			Component component = repository.findById(1L);
			log.info("Customer found with findById(1L):");
			log.info(component.toString());
			*/

			log.info("Components that are berries:");
			componentRepo.findByClassification("Berry").forEach(berry -> {
				log.info(berry.toString());
			});

		};
	}

}
