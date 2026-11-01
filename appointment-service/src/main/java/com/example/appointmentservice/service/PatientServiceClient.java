package com.example.appointmentservice.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientServiceClient {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceClient.class);
    private final RestTemplate restTemplate;

    public PatientServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "patientRetry", fallbackMethod = "patientFallback")
    public ResponseEntity<String> getPatientInfo(String patientId) {
        log.info("Dang thuc hien goi sang Patient-Service voi patientId: {}", patientId);
        String patientServiceUrl = "http://localhost:8084/api/patients/" + patientId;

        String response = restTemplate.getForObject(patientServiceUrl, String.class);
        return ResponseEntity.ok(response);
    }

    // Fallback duoc goi khi da thu lai du 3 lan ma van that bai
    public ResponseEntity<String> patientFallback(String patientId, Throwable t) {
        log.error("Patient-Service khong phan hoi sau khi da thu lai (Retry). Nguyen nhan: {}", t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Patient-Service hien tai khong kha dung sau 3 lan thu lai. Vui long thu lai sau!");
    }
}