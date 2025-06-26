package com.cabz.fhir_normaliser.integration;

import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.model.Gender;
import com.cabz.fhir_normaliser.model.MappedPatient;
import com.cabz.fhir_normaliser.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static com.cabz.fhir_normaliser.model.Gender.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class PresenterControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientRepository repository;

    private static final String BUNDLE_ENDPOINT = "/api/data/patients";

    private static final String FHIR_ID = "fhirId";
    private static final String GIVEN_NAME = "givenName";
    private static final String FAMILY_NAME = "familyName";
    private static final Gender GENDER = MALE;
    private static final LocalDate BIRTH_DATE = LocalDate.of(2000, 1, 1);

    @Test
    void shouldReadMappedPatientsFromDatabase() {

        addBundle();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<List<PatientResponseDto>> response = restTemplate.exchange(
                "http://localhost:" + port + BUNDLE_ENDPOINT,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        List<MappedPatient> all = repository.findAll();
        assertEquals(1, all.size());
        MappedPatient mappedPatient = all.getFirst();
        assertEquals(FHIR_ID, mappedPatient.getFhirId());
        assertEquals(GIVEN_NAME, mappedPatient.getGivenName());
        assertEquals(FAMILY_NAME, mappedPatient.getFamilyName());
        assertEquals(GENDER, mappedPatient.getGender());
        assertEquals(BIRTH_DATE, mappedPatient.getBirthDate());
    }

    private void addBundle() {
        MappedPatient mappedPatient = MappedPatient.builder()
                .fhirId(FHIR_ID)
                .givenName(GIVEN_NAME)
                .familyName(FAMILY_NAME)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .build();
        repository.save(mappedPatient);
    }
}
