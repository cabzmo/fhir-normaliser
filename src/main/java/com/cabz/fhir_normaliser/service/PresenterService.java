package com.cabz.fhir_normaliser.service;

import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.model.MappedPatient;
import com.cabz.fhir_normaliser.repository.PatientRepository;
import com.cabz.fhir_normaliser.util.PatientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PresenterService {

    @Autowired
    private PatientRepository repository;

    public List<PatientResponseDto> getAllPatients() {
        List<MappedPatient> patients = repository.findAll();
        return patients.stream()
                .map(PatientMapper.INSTANCE::map).collect(Collectors.toList());
    }
} 