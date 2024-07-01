package com.langat.DronesAssignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DronesAssignmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronesAssignmentApplication.class, args);
	}

}
