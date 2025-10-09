package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.ClaseCreateDto;
import com.example.tp_grupo6.dtos.ClaseDto;
import com.example.tp_grupo6.entities.Clase;
import com.example.tp_grupo6.services.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clases")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClaseController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllClases() {
        try {
            List<ClaseDto> clases = claseService.getAllClases();
            Map<String, Object> response = new HashMap<>();
            response.put("clases", clases);
            response.put("total", clases.size());
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al obtener las clases", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getClaseById(@PathVariable Long id) {
        try {
            ClaseDto clase = claseService.getClaseById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("clase", clase);
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Clase no encontrada", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno del servidor", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createClase(@Valid @RequestBody ClaseCreateDto claseCreateDto) {
        try {
            ClaseDto nuevaClase = claseService.createClase(claseCreateDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Clase creada exitosamente");
            response.put("clase", nuevaClase);
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al crear la clase", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateClase(@PathVariable Long id, @Valid @RequestBody ClaseCreateDto claseCreateDto) {
        try {
            ClaseDto claseActualizada = claseService.updateClase(id, claseCreateDto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Clase actualizada exitosamente");
            response.put("clase", claseActualizada);
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Clase no encontrada", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Error al actualizar la clase", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteClase(@PathVariable Long id) {
        try {
            claseService.deleteClase(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Clase eliminada exitosamente");
            response.put("claseId", id);
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Clase no encontrada", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar la clase", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('INSTRUCTOR') or hasRole('ADMIN')")
    public ResponseEntity<?> searchClases(@RequestParam(required = false) String nombre,
                                         @RequestParam(required = false) String instructor) {
        try {
            List<ClaseDto> clases = claseService.searchClases(nombre, instructor);
            Map<String, Object> response = new HashMap<>();
            response.put("clases", clases);
            response.put("total", clases.size());
            response.put("searchParams", Map.of("nombre", nombre != null ? nombre : "N/A", 
                                              "instructor", instructor != null ? instructor : "N/A"));
            response.put("timestamp", LocalDateTime.now());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error en la búsqueda", 
                               "message", e.getMessage(),
                               "timestamp", LocalDateTime.now()));
        }
    }
}