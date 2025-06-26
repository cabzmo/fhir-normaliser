package com.cabz.fhir_normaliser.controller;

import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.service.PresenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/presentation")
public class PresenterController {

    @Autowired
    private PresenterService presenterService;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patients = presenterService.getAllPatients();
        return ResponseEntity.ok(patients);
    }
}