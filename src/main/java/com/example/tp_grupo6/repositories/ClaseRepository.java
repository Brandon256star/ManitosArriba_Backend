package com.example.tp_grupo6.repositories;

import com.example.tp_grupo6.entities.Clase;
import com.example.tp_grupo6.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
    
    List<Clase> findByActivaTrue();
    
    List<Clase> findByInstructor(Usuario instructor);
    
    List<Clase> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT c FROM Clase c WHERE c.instructor.nombre LIKE %:nombre% OR c.instructor.apellido LIKE %:nombre%")
    List<Clase> findByInstructorNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT c FROM Clase c WHERE c.nombre LIKE %:nombre% AND (c.instructor.nombre LIKE %:instructor% OR c.instructor.apellido LIKE %:instructor%)")
    List<Clase> findByNombreContainingIgnoreCaseAndInstructorNombreContainingIgnoreCase(@Param("nombre") String nombre, @Param("instructor") String instructor);
}