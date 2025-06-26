package com.cabz.fhir_normaliser;

import com.cabz.fhir_normaliser.model.MappedPatient;

import java.time.LocalDate;

import static com.cabz.fhir_normaliser.model.Gender.MALE;

public class BuilderTest {
    public static void main(String[] args) {
        MappedPatient p = MappedPatient.builder()
                .fhirId("123")
                .givenName("John")
                .familyName("Doe")
                .gender(MALE)
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        System.out.println(p);
    }
}

