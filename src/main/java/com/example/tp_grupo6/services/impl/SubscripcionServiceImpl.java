package com.example.tp_grupo6.services.impl;

import com.example.tp_grupo6.dtos.SubscripcionCreateDto;
import com.example.tp_grupo6.dtos.SubscripcionDto;
import com.example.tp_grupo6.entities.Subscripcion;
import com.example.tp_grupo6.repositories.SubscripcionRepository;
import com.example.tp_grupo6.services.SubscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubscripcionServiceImpl implements SubscripcionService {

    @Autowired
    private SubscripcionRepository subscripcionRepository;

    @Override
    public List<SubscripcionDto> getAllSubscripciones() {
        List<Subscripcion> subscripciones = subscripcionRepository.findAll();
        return subscripciones.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscripcionDto getSubscripcionById(Long id) {
        Subscripcion subscripcion = subscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada con ID: " + id));
        return convertToDto(subscripcion);
    }

    @Override
    public SubscripcionDto createSubscripcion(SubscripcionCreateDto subscripcionCreateDto) {
        Subscripcion subscripcion = new Subscripcion();
        subscripcion.setTipoSubscripcion(subscripcionCreateDto.getTipoSubscripcion());
        subscripcion.setDescripcion(subscripcionCreateDto.getDescripcion());
        subscripcion.setPrecio(BigDecimal.valueOf(subscripcionCreateDto.getPrecio()));
        subscripcion.setDuracionDias(subscripcionCreateDto.getDuracionDias());
        subscripcion.setFechaCreacion(LocalDateTime.now());
        subscripcion.setActiva(true);
        
        Subscripcion savedSubscripcion = subscripcionRepository.save(subscripcion);
        return convertToDto(savedSubscripcion);
    }

    @Override
    public SubscripcionDto updateSubscripcion(Long id, SubscripcionCreateDto subscripcionCreateDto) {
        Subscripcion subscripcionExistente = subscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada con ID: " + id));
        
        subscripcionExistente.setTipoSubscripcion(subscripcionCreateDto.getTipoSubscripcion());
        subscripcionExistente.setDescripcion(subscripcionCreateDto.getDescripcion());
        subscripcionExistente.setPrecio(BigDecimal.valueOf(subscripcionCreateDto.getPrecio()));
        subscripcionExistente.setDuracionDias(subscripcionCreateDto.getDuracionDias());
        subscripcionExistente.setFechaActualizacion(LocalDateTime.now());
        
        Subscripcion updatedSubscripcion = subscripcionRepository.save(subscripcionExistente);
        return convertToDto(updatedSubscripcion);
    }

    @Override
    public void deleteSubscripcion(Long id) {
        if (!subscripcionRepository.existsById(id)) {
            throw new RuntimeException("Suscripción no encontrada con ID: " + id);
        }
        subscripcionRepository.deleteById(id);
    }

    @Override
    public List<SubscripcionDto> getSubscripcionesActivas() {
        List<Subscripcion> subscripciones = subscripcionRepository.findByActivaTrue();
        return subscripciones.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private SubscripcionDto convertToDto(Subscripcion subscripcion) {
        SubscripcionDto dto = new SubscripcionDto();
        dto.setId(subscripcion.getId());
        dto.setTipoSubscripcion(subscripcion.getTipoSubscripcion());
        dto.setDescripcion(subscripcion.getDescripcion());
        dto.setPrecio(subscripcion.getPrecio().doubleValue());
        dto.setDuracionDias(subscripcion.getDuracionDias());
        dto.setFechaCreacion(subscripcion.getFechaCreacion());
        dto.setFechaActualizacion(subscripcion.getFechaActualizacion());
        dto.setActiva(subscripcion.getActiva());
        return dto;
    }
}