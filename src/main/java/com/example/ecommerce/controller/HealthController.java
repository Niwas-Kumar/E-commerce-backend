package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @CrossOrigin(origins = "*")  // allow all origins
    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}
