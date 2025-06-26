package com.cabz.fhir_normaliser.unit;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.model.Gender;
import com.cabz.fhir_normaliser.model.MappedPatient;
import com.cabz.fhir_normaliser.repository.PatientRepository;
import com.cabz.fhir_normaliser.service.PresenterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.cabz.fhir_normaliser.model.Gender.FEMALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PresenterServiceTest {

    @InjectMocks
    private PresenterService presenterService;

    @Mock
    private PatientRepository patientRepository;

    private IParser parser;

    private static final String FHIR_ID = "urn:uuid:8c95253e-8ee8-9ae8-6d40-021d702dc78e";
    private static final String GIVEN_NAME = "Alice";
    private static final String FAMILY_NAME = "Smith";
    private static final Gender GENDER = FEMALE;
    private static final LocalDate BIRTH_DATE = LocalDate.of(1944, 8, 27);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = FhirContext.forR4().newJsonParser();
    }

    @Test
    void shouldReturnAllMappedPatients() {

        PatientResponseDto expectedPatientDto = PatientResponseDto.builder()
                .fhirId(FHIR_ID)
                .firstName(GIVEN_NAME)
                .lastName(FAMILY_NAME)
                .gender(String.valueOf(GENDER))
                .birthDate(String.valueOf(BIRTH_DATE))
                .build();

        MappedPatient mappedPatient = MappedPatient.builder()
                .id(1L)
                .fhirId(FHIR_ID)
                .givenName(GIVEN_NAME)
                .familyName(FAMILY_NAME)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .build();

        when(patientRepository.findAll()).thenReturn(Collections.singletonList(mappedPatient));

        List<PatientResponseDto> result = presenterService.getAllPatients();

        assertEquals(1, result.size());
        assertNotNull(result.getFirst());

        PatientResponseDto actualPatientDto = result.getFirst();

        assertEquals(expectedPatientDto.getFhirId(), actualPatientDto.getFhirId());
        assertEquals(expectedPatientDto.getFirstName(), actualPatientDto.getFirstName());
        assertEquals(expectedPatientDto.getLastName(), actualPatientDto.getLastName());
        assertEquals(expectedPatientDto.getGender(), actualPatientDto.getGender());
        assertEquals(expectedPatientDto.getBirthDate(), actualPatientDto.getBirthDate());

        verify(patientRepository).findAll();
    }
}
