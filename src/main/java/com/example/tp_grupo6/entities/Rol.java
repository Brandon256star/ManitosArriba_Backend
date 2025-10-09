package com.example.tp_grupo6.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    
    @NotBlank(message = "El tipo de rol es obligatorio")
    @Column(unique = true, nullable = false, name = "tipo_rol")
    private String tipoRol;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios;
    
    public Rol(String tipoRol, String descripcion) {
        this.tipoRol = tipoRol;
        this.descripcion = descripcion;
    }
}