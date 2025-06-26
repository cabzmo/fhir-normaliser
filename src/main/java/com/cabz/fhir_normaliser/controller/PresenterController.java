package com.cabz.fhir_normaliser.controller;

import com.cabz.fhir_normaliser.service.PresenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/presentation")
public class PresenterController {

    @Autowired
    private PresenterService presenterService;

//    @GetMapping("/patients")
//    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
//        List<PatientResponseDto> dtos = presenterService.getAllPatientsAsDTOs();
//        return ResponseEntity.ok(dtos);
//    }
}