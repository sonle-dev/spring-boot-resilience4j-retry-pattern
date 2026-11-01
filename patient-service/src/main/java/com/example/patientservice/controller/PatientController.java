package com.example.patientservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final AtomicInteger attempts = new AtomicInteger(0);

    @GetMapping("/{patientId}")
    public ResponseEntity<String> getPatientById(@PathVariable String patientId) {
        int currentAttempt = attempts.incrementAndGet();

        // Lần 1: Giả lập lỗi timeout/mất kết nối tạm thời
        if (currentAttempt % 2 != 0) {
            throw new RuntimeException("Simulated Network Transient Failure");
        }

        // Lần 2: Thành công bình thường
        return ResponseEntity.ok("Thong tin benh nhan: Nguyen Van A (ID: " + patientId + ") - Thanh cong tai lan goi thu " + currentAttempt);
    }
}