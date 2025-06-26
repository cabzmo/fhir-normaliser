package com.cabz.fhir_normaliser.integration;

import com.cabz.fhir_normaliser.model.MappedPatient;
import com.cabz.fhir_normaliser.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class NormaliserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientRepository repository;

    private static final String BUNDLE_ENDPOINT = "/api/fhir/bundle";

    private static final String FHIR_ID = "urn:uuid:8c95253e-8ee8-9ae8-6d40-021d702dc78e";

    @Test
    void shouldSaveMappedPatientsToDatabase() throws IOException {

        String bundle = Files.readString(new ClassPathResource("bundles/valid-patient.json").getFile().toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(bundle, headers);

        ResponseEntity<List<MappedPatient>> response = restTemplate.exchange(
                "http://localhost:" + port + BUNDLE_ENDPOINT,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        List<MappedPatient> all = repository.findAll();
        assertEquals(1, all.size());
        MappedPatient mappedPatient = all.getFirst();
        assertEquals(FHIR_ID, mappedPatient.getFhirId());
    }
}
