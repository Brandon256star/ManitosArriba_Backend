package com.example.tp_grupo6.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscripcionCreateDto {
    
    @NotBlank(message = "El tipo de suscripción es obligatorio")
    @Size(min = 3, max = 50, message = "El tipo debe tener entre 3 y 50 caracteres")
    private String tipoSubscripcion;
    
    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private Double precio;
    
    @NotNull(message = "La duración en días es obligatoria")
    @Positive(message = "La duración debe ser mayor a 0")
    private Integer duracionDias;
}