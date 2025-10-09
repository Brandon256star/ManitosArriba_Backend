package com.example.tp_grupo6.services;

import com.example.tp_grupo6.dtos.SubscripcionCreateDto;
import com.example.tp_grupo6.dtos.SubscripcionDto;

import java.util.List;

public interface SubscripcionService {
    
    List<SubscripcionDto> getAllSubscripciones();
    
    List<SubscripcionDto> getSubscripcionesActivas();
    
    SubscripcionDto getSubscripcionById(Long id);
    
    SubscripcionDto createSubscripcion(SubscripcionCreateDto subscripcionCreateDto);
    
    SubscripcionDto updateSubscripcion(Long id, SubscripcionCreateDto subscripcionCreateDto);
    
    void deleteSubscripcion(Long id);
}