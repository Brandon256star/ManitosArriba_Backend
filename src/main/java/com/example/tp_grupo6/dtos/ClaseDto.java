package com.example.tp_grupo6.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaseDto {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String instructorNombre;
    private Long instructorId;
    private Integer capacidadMaxima;
    private String ubicacion;
    private String modalidad;
    private Integer inscriptosActuales;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Boolean activa;
}