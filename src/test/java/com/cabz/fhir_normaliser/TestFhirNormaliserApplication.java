package com.cabz.fhir_normaliser;

import org.springframework.boot.SpringApplication;

public class TestFhirNormaliserApplication {

	public static void main(String[] args) {
		SpringApplication.from(FhirNormaliserApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
