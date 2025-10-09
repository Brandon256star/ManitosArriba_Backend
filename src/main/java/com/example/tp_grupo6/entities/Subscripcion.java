package com.example.tp_grupo6.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subscripciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscripcion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El tipo de suscripción es obligatorio")
    @Column(nullable = false, name = "tipo_subscripcion")
    private String tipoSubscripcion;
    
    @Column(length = 500)
    private String descripcion;
    
    @DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(name = "duracion_dias")
    private Integer duracionDias;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "activa")
    private Boolean activa = true;
    
    @OneToMany(mappedBy = "subscripcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsuarioSubscripcion> usuarioSubscripciones;
}