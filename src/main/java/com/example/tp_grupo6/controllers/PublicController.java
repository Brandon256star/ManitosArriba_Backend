package com.example.tp_grupo6.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PublicController {

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", System.currentTimeMillis());
        health.put("service", "TP_Grupo6 Authentication Service");
        health.put("version", "1.0.0");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("applicationName", "TP Grupo 6 - Sistema de Aprendizaje");
        info.put("version", "1.0.0");
        info.put("description", "Sistema de gestión de cursos y aprendizaje con funcionalidades de feedback y gamificación");
        info.put("features", Map.of(
            "authentication", "JWT-based authentication",
            "youthFriendlyInterface", "Optimized for young users",
            "feedbackSystem", "User feedback collection",
            "userIncentives", "24/7 engagement system",
            "subscriptions", "Subscription management",
            "classes", "Educational content management"
        ));
        info.put("endpoints", Map.of(
            "auth", "/api/auth",
            "users", "/api/usuarios",
            "feedback", "/api/feedback",
            "public", "/api/public"
        ));
        return ResponseEntity.ok(info);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "active");
        status.put("message", "Sistema funcionando correctamente");
        status.put("feedbackEnabled", true);
        status.put("authenticationEnabled", true);
        status.put("version", "1.0.0");
        status.put("uptime", System.currentTimeMillis());
        return ResponseEntity.ok(status);
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, Object> welcome = new HashMap<>();
        welcome.put("message", "¡Bienvenido al Sistema TP Grupo 6!");
        welcome.put("description", "Plataforma educativa con sistema de feedback y gamificación");
        welcome.put("instructions", Map.of(
            "1", "Regístrate usando /api/auth/signup",
            "2", "Inicia sesión con /api/auth/signin", 
            "3", "Envía feedback usando /api/feedback/submit",
            "4", "Explora las funcionalidades disponibles"
        ));
        welcome.put("supportedRoles", new String[]{"USER", "ADMIN", "INSTRUCTOR"});
        return ResponseEntity.ok(welcome);
    }
}