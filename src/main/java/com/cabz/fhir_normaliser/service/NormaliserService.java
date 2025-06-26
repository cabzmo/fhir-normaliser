package com.cabz.fhir_normaliser.service;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.cabz.fhir_normaliser.model.MappedPatient;
import com.cabz.fhir_normaliser.repository.PatientRepository;
import com.cabz.fhir_normaliser.util.PatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NormaliserService {

    private final PatientRepository repository;
    private final FhirContext fhirContext = FhirContext.forR4();

    public NormaliserService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<MappedPatient> processBundle(String json) {
        IParser parser = fhirContext.newJsonParser();
        Bundle bundle = parser.parseResource(Bundle.class, json);

        List<Patient> fhirPatients = new ArrayList<>();
        for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
            if (entry.getResource() instanceof Patient p) {
                fhirPatients.add(p);
            }
        }

        List<MappedPatient> mappedPatients = fhirPatients.stream()
                .map(PatientMapper.INSTANCE::map)
                .collect(Collectors.toList());

        repository.saveAll(mappedPatients);
        return mappedPatients;
    }
}
