package com.example.tp_grupo6.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {
    
    private Long idFeedback;
    private String mensaje;
    private Integer puntuacion;
    private String categoria;
    private String nombreUsuario;
    private Long usuarioId;
    private LocalDateTime fechaCreacion;
    private Boolean procesado;
}