package com.example.tp_grupo6.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminTestController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "¡Acceso de administrador exitoso!");
        response.put("timestamp", LocalDateTime.now());
        response.put("userRole", "ADMIN");
        response.put("permissions", new String[]{"READ", "WRITE", "DELETE", "MANAGE_USERS", "VIEW_ANALYTICS"});
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> userTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "¡Acceso de usuario exitoso!");
        response.put("timestamp", LocalDateTime.now());
        response.put("userRole", "USER");
        response.put("permissions", new String[]{"READ", "SUBMIT_FEEDBACK", "VIEW_PROFILE"});
        return ResponseEntity.ok(response);
    }

    @GetMapping("/instructor")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> instructorTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "¡Acceso de instructor exitoso!");
        response.put("timestamp", LocalDateTime.now());
        response.put("userRole", "INSTRUCTOR");
        response.put("permissions", new String[]{"READ", "WRITE", "MANAGE_CLASSES", "VIEW_STUDENTS"});
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public")
    public ResponseEntity<?> publicTest() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "¡Endpoint público - sin autenticación requerida!");
        response.put("timestamp", LocalDateTime.now());
        response.put("access", "PUBLIC");
        response.put("description", "Este endpoint es accesible sin token JWT");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/echo")
    public ResponseEntity<?> echo(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Echo test - datos recibidos correctamente");
        response.put("timestamp", LocalDateTime.now());
        response.put("receivedData", requestBody);
        response.put("dataType", requestBody.getClass().getSimpleName());
        return ResponseEntity.ok(response);
    }
}