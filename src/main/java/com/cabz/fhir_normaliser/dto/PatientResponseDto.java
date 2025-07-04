package com.cabz.fhir_normaliser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponseDto {
    private String fhirId;
    private String firstName;
    private String lastName;
    private String gender;
    private String birthDate;
}