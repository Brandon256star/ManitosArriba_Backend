package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Subscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscripcionRepository extends JpaRepository<Subscripcion, Long> {
    
    List<Subscripcion> findByActivaTrue();
    Optional<Subscripcion> findByTipoSubscripcion(String tipoSubscripcion);
    boolean existsByTipoSubscripcion(String tipoSubscripcion);
}