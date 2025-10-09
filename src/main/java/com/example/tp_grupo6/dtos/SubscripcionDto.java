package com.example.tp_grupo6.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscripcionDto {
    
    private Long id;
    private String tipoSubscripcion;
    private String descripcion;
    private Double precio;
    private Integer duracionDias;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Boolean activa;
}