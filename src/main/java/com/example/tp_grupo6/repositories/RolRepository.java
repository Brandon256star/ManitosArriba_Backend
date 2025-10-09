package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    
    Optional<Rol> findByTipoRol(String tipoRol);
    boolean existsByTipoRol(String tipoRol);
}