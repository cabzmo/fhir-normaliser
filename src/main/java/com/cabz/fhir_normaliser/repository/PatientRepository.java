package com.cabz.fhir_normaliser.repository;

import com.cabz.fhir_normaliser.model.MappedPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<MappedPatient, Long> {
}
