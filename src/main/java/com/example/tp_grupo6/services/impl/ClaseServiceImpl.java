package com.example.tp_grupo6.services.impl;

import com.example.tp_grupo6.dtos.ClaseCreateDto;
import com.example.tp_grupo6.dtos.ClaseDto;
import com.example.tp_grupo6.entities.Clase;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.repositories.ClaseRepository;
import com.example.tp_grupo6.repositories.UsuarioRepository;
import com.example.tp_grupo6.services.ClaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseRepository claseRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ClaseDto> getAllClases() {
        List<Clase> clases = claseRepository.findAll();
        return clases.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClaseDto getClaseById(Long id) {
        Clase clase = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
        return convertToDto(clase);
    }

    @Override
    public ClaseDto createClase(ClaseCreateDto claseCreateDto) {
        // Validar que el instructor existe
        Usuario instructor = usuarioRepository.findById(claseCreateDto.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con ID: " + claseCreateDto.getInstructorId()));
        
        Clase clase = new Clase();
        clase.setNombre(claseCreateDto.getNombre());
        clase.setDescripcion(claseCreateDto.getDescripcion());
        clase.setFechaInicio(claseCreateDto.getFechaInicio());
        clase.setFechaFin(claseCreateDto.getFechaFin());
        clase.setInstructor(instructor);
        clase.setCapacidadMaxima(claseCreateDto.getCapacidadMaxima());
        clase.setUbicacion(claseCreateDto.getUbicacion());
        clase.setModalidad(claseCreateDto.getModalidad());
        clase.setFechaCreacion(LocalDateTime.now());
        clase.setActiva(true);
        
        Clase savedClase = claseRepository.save(clase);
        return convertToDto(savedClase);
    }

    @Override
    public ClaseDto updateClase(Long id, ClaseCreateDto claseCreateDto) {
        Clase claseExistente = claseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + id));
        
        // Validar que el instructor existe si se está cambiando
        if (claseCreateDto.getInstructorId() != null) {
            Usuario instructor = usuarioRepository.findById(claseCreateDto.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor no encontrado con ID: " + claseCreateDto.getInstructorId()));
            claseExistente.setInstructor(instructor);
        }
        
        claseExistente.setNombre(claseCreateDto.getNombre());
        claseExistente.setDescripcion(claseCreateDto.getDescripcion());
        claseExistente.setFechaInicio(claseCreateDto.getFechaInicio());
        claseExistente.setFechaFin(claseCreateDto.getFechaFin());
        claseExistente.setCapacidadMaxima(claseCreateDto.getCapacidadMaxima());
        claseExistente.setUbicacion(claseCreateDto.getUbicacion());
        claseExistente.setModalidad(claseCreateDto.getModalidad());
        claseExistente.setFechaActualizacion(LocalDateTime.now());
        
        Clase updatedClase = claseRepository.save(claseExistente);
        return convertToDto(updatedClase);
    }

    @Override
    public void deleteClase(Long id) {
        if (!claseRepository.existsById(id)) {
            throw new RuntimeException("Clase no encontrada con ID: " + id);
        }
        claseRepository.deleteById(id);
    }

    @Override
    public List<ClaseDto> searchClases(String nombre, String instructor) {
        List<Clase> clases;
        
        if (nombre != null && instructor != null) {
            clases = claseRepository.findByNombreContainingIgnoreCaseAndInstructorNombreContainingIgnoreCase(nombre, instructor);
        } else if (nombre != null) {
            clases = claseRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (instructor != null) {
            clases = claseRepository.findByInstructorNombreContainingIgnoreCase(instructor);
        } else {
            clases = claseRepository.findAll();
        }
        
        return clases.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private ClaseDto convertToDto(Clase clase) {
        ClaseDto dto = new ClaseDto();
        dto.setId(clase.getId());
        dto.setNombre(clase.getNombre());
        dto.setDescripcion(clase.getDescripcion());
        dto.setFechaInicio(clase.getFechaInicio());
        dto.setFechaFin(clase.getFechaFin());
        
        if (clase.getInstructor() != null) {
            dto.setInstructorId(clase.getInstructor().getIdUsuario());
            dto.setInstructorNombre(clase.getInstructor().getNombre() + " " + 
                                   (clase.getInstructor().getApellido() != null ? clase.getInstructor().getApellido() : ""));
        }
        
        dto.setCapacidadMaxima(clase.getCapacidadMaxima());
        dto.setUbicacion(clase.getUbicacion());
        dto.setModalidad(clase.getModalidad());
        dto.setFechaCreacion(clase.getFechaCreacion());
        dto.setFechaActualizacion(clase.getFechaActualizacion());
        dto.setActiva(clase.getActiva());
        
        // Calcular inscritos actuales (esto se podría hacer con una consulta más eficiente)
        dto.setInscriptosActuales(0); // Por ahora, se puede implementar más tarde con la relación UsuarioClase
        
        return dto;
    }
}