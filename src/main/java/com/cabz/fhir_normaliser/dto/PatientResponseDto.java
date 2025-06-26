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
    private String nhsNumber;
    private String fullName;
    private String gender;
    private String dateOfBirth;
    private String address;
}