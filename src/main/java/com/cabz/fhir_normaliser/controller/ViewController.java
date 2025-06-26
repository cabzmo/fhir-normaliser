package com.cabz.fhir_normaliser.controller;

import com.cabz.fhir_normaliser.dto.PatientResponseDto;
import com.cabz.fhir_normaliser.service.NormaliserService;
import com.cabz.fhir_normaliser.service.PresenterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class ViewController {

    @Autowired
    private NormaliserService normaliserService;

    @Autowired
    private PresenterService presenterService;

    @GetMapping("/read/patients")
    public String viewPatientsPage(Model model,
                                   @RequestParam(value = "success", required = false) Boolean success,
                                   @RequestParam(value = "message", required = false) String message
                                   ) {
        List<PatientResponseDto> patients = presenterService.getAllPatients();
        model.addAttribute("patients", patients);
        if (message != null && success != null) {
            model.addAttribute("message", message);
            model.addAttribute("success", success);
        }
        return "patients";
    }

    @PostMapping("/write/bundle")
    public ResponseEntity<Map<String, Object>> uploadJsonBundle(@RequestBody String bundleJson) {
        Map<String, Object> response = new HashMap<>();
        try {
            normaliserService.processBundle(bundleJson);
            List<PatientResponseDto> patientResponseDtoList = presenterService.getAllPatients();
            response.put("message", "Patients saved successfully");
            response.put("patients", patientResponseDtoList);
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", "Failed to parse bundle");
            response.put("error", e.getMessage());
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleExceptions(Exception ex, HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
