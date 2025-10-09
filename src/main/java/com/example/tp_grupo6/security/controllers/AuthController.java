package com.example.tp_grupo6.security.controllers;

import com.example.tp_grupo6.security.dtos.AuthResponse;
import com.example.tp_grupo6.security.dtos.LoginRequest;
import com.example.tp_grupo6.security.dtos.RegisterRequest;
import com.example.tp_grupo6.security.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.authenticateUser(loginRequest);
            if (response.getToken() != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse("Error de autenticación: " + e.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        try {
            AuthResponse response = authService.registerUser(signUpRequest);
            if (response.getMessage() != null && response.getMessage().contains("correctamente")) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponse("Error en el registro: " + e.getMessage()));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok(Map.of("message", "Sesión cerrada exitosamente"));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            // Remover "Bearer " del token
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            return ResponseEntity.ok(Map.of(
                "valid", true,
                "message", "Token válido"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "valid", false,
                "message", "Token inválido"
            ));
        }
    }
}