package com.example.tp_grupo6.services;

import com.example.tp_grupo6.dtos.ClaseCreateDto;
import com.example.tp_grupo6.dtos.ClaseDto;

import java.util.List;

public interface ClaseService {
    
    List<ClaseDto> getAllClases();
    
    ClaseDto getClaseById(Long id);
    
    ClaseDto createClase(ClaseCreateDto claseCreateDto);
    
    ClaseDto updateClase(Long id, ClaseCreateDto claseCreateDto);
    
    void deleteClase(Long id);
    
    List<ClaseDto> searchClases(String nombre, String instructor);
}