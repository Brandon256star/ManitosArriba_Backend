package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Feedback;
import com.example.tp_grupo6.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    List<Feedback> findByUsuario(Usuario usuario);
    List<Feedback> findByProcesado(boolean procesado);
    List<Feedback> findByCategoria(String categoria);
    
    @Query("SELECT f FROM Feedback f ORDER BY f.fechaCreacion DESC")
    List<Feedback> findAllOrderByFechaCreacionDesc();
    
    @Query("SELECT AVG(f.puntuacion) FROM Feedback f")
    Double getPromedioPuntuacion();
}