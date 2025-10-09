package com.example.tp_grupo6.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String rol;
    private String message;
    
    public AuthResponse(String token, Long id, String username, String email, String rol) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.rol = rol;
    }
    
    public AuthResponse(String message) {
        this.message = message;
    }
}