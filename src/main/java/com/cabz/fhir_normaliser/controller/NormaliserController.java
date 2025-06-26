package com.cabz.fhir_normaliser.controller;

import com.cabz.fhir_normaliser.model.MappedPatient;
import com.cabz.fhir_normaliser.service.NormaliserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fhir")
public class NormaliserController {

    @Autowired
    private NormaliserService normaliserService;

    @PostMapping("/bundle")
    public ResponseEntity<List<MappedPatient>> ingestBundle(@RequestBody String bundleJson) {
        List<MappedPatient> mappedPatientList = normaliserService.processBundle(bundleJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(mappedPatientList);
    }
}
