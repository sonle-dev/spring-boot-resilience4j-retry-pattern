package com.example.appointmentservice.controller;

import com.example.appointmentservice.service.PatientServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientClientController {

    private final PatientServiceClient patientServiceClient;

    public PatientClientController(PatientServiceClient patientServiceClient) {
        this.patientServiceClient = patientServiceClient;
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<String> getPatient(@PathVariable String patientId) {
        return patientServiceClient.getPatientInfo(patientId);
    }
}