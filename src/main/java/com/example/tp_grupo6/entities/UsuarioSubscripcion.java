package com.example.tp_grupo6.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_subscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSubscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuarioSubscripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscripcion_id", nullable = false)
    private Subscripcion subscripcion;
    
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio = LocalDateTime.now();
    
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;
    
    @Column(name = "activo")
    private boolean activo = true;
    
    @PrePersist
    public void prePersist() {
        if (fechaInicio == null) {
            fechaInicio = LocalDateTime.now();
        }
        if (fechaVencimiento == null && subscripcion != null) {
            fechaVencimiento = fechaInicio.plusDays(subscripcion.getDuracionDias());
        }
    }
}