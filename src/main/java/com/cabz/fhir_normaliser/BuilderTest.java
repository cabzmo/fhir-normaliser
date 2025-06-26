package com.cabz.fhir_normaliser;

import com.cabz.fhir_normaliser.model.MappedPatient;

public class BuilderTest {
    public static void main(String[] args) {
        MappedPatient p = MappedPatient.builder()
                .fhirId("123")
                .givenName("John")
                .familyName("Doe")
                .gender("male")
                .birthDate("2000-01-01")
                .build();

        System.out.println(p);
    }
}

