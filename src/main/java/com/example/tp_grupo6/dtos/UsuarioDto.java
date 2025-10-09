package com.example.tp_grupo6.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    
    private Long id;
    private String username;
    private String email;
    private String nombre;
    private String apellido;
    private String rolTipo;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimoAcceso;
}