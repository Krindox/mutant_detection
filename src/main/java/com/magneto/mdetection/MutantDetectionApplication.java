package com.magneto.mdetection;

import com.magneto.mdetection.controllers.HumanController;
import com.magneto.mdetection.entities.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.function.Function;

@SpringBootApplication
public class MutantDetectionApplication extends SpringBootServletInitializer {

	@Autowired
	HumanController humanController;

	@Bean
	public Function<Human, ResponseEntity<Boolean>> isMutant(){
		return (input) -> humanController.isMutant(input);
	}

	public static void main(String[] args) {
		SpringApplication.run(MutantDetectionApplication.class, args);
	}

}
