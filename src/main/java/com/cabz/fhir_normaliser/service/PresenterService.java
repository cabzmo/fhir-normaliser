package com.cabz.fhir_normaliser.service;

import com.cabz.fhir_normaliser.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresenterService {

    @Autowired
    private PatientRepository repository;

//    public List<PatientResponseDto> getAllPatients() {
//        List<MappedPatient> patients = repository.findAll();
//        return patients.stream()
//                .map(PatientMapper.INSTANCE::map).collect(Collectors.toList());
//    }
} 