package com.rydzo.gatewy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class GatewayHealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Rydzo API Gateway is running and routing requests");
        response.put("service", "rydzo-api-gateway");
        response.put("version", "1.0.0");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/services")
    public ResponseEntity<Map<String, String>> servicesInfo() {
        Map<String, String> services = new HashMap<>();
        services.put("user-service", "http://localhost:8081");
        services.put("vendor-service", "http://localhost:8082");
        services.put("vehicle-service", "http://localhost:8083");
        services.put("local-carpool-service", "http://localhost:8084");
        services.put("intercity-pool-service", "http://localhost:8085");
        services.put("booking-service", "http://localhost:8086");
        services.put("payment-service", "http://localhost:8087");
        services.put("notification-service", "http://localhost:8088");
        return ResponseEntity.ok(services);
    }
}

