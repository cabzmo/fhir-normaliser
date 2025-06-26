package com.cabz.fhir_normaliser.controller;

import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.service.PresenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class PresenterController {

    @Autowired
    private PresenterService presenterService;

    @GetMapping("data/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        List<PatientResponseDto> patients = presenterService.getAllPatients();
        return ResponseEntity.ok(patients);
    }



}