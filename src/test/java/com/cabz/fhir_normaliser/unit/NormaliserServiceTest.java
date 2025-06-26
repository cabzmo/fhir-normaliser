package com.cabz.fhir_normaliser.unit;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.cabz.fhir_normaliser.model.Gender;
import com.cabz.fhir_normaliser.model.MappedPatient;
import com.cabz.fhir_normaliser.repository.PatientRepository;
import com.cabz.fhir_normaliser.service.NormaliserService;
import com.cabz.fhir_normaliser.util.PatientMapper;
import org.hl7.fhir.r4.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

import static com.cabz.fhir_normaliser.model.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class NormaliserServiceTest {

    @InjectMocks
    private NormaliserService normaliserService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    private IParser parser;

    private static final String FHIR_ID = "urn:uuid:8c95253e-8ee8-9ae8-6d40-021d702dc78e";
    private static final String GIVEN_NAME = "Aaron697";
    private static final String FAMILY_NAME = "Dickens475";
    private static final Gender GENDER = MALE;
    private static final LocalDate BIRTH_DATE = LocalDate.of(1944, 8, 27);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        parser = FhirContext.forR4().newJsonParser();
    }

    @Test
    void shouldParseAndSavePatients() throws IOException {

        String bundleJson = Files.readString(new ClassPathResource("bundles/valid-patient.json").getFile().toPath());

        MappedPatient mappedPatient = MappedPatient.builder()
                .fhirId(FHIR_ID)
                .familyName(FAMILY_NAME)
                .givenName(GIVEN_NAME)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .build();

        when(patientMapper.map(any(Patient.class))).thenReturn(mappedPatient);
        when(patientRepository.saveAll(any())).thenReturn(List.of(mappedPatient));

        List<MappedPatient> result = normaliserService.processBundle(bundleJson);

        assertEquals(1, result.size());
        assertNotNull(result.getFirst());

        MappedPatient processedPatient = result.getFirst();

        assertEquals(FHIR_ID, processedPatient.getFhirId());
        assertEquals(GIVEN_NAME, processedPatient.getGivenName());
        assertEquals(FAMILY_NAME, processedPatient.getFamilyName());
        assertEquals(GENDER, processedPatient.getGender());
        assertEquals(BIRTH_DATE, processedPatient.getBirthDate());

//        verify(patientMapper, times(1)).map(any(Patient.class));
        verify(patientRepository, times(1)).saveAll(any());
    }
}
