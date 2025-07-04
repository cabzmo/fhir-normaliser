package com.cabz.fhir_normaliser.repository;

import com.cabz.fhir_normaliser.model.MappedPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<MappedPatient, Long> {

    Optional<MappedPatient> findByFhirId(String fhirId);
}
