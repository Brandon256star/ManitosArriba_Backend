package com.example.tp_grupo6.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioClase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuarioClase;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clase_id", nullable = false)
    private Clase clase;
    
    @Column(name = "fecha_inscripcion")
    private LocalDateTime fechaInscripcion = LocalDateTime.now();
    
    @Column(name = "completado")
    private boolean completado = false;
    
    @Column(name = "progreso")
    private int progreso = 0; // Porcentaje de 0-100
}