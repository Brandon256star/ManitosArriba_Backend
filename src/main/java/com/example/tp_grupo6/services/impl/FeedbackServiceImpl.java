package com.example.tp_grupo6.services.impl;

import com.example.tp_grupo6.dtos.FeedbackDto;
import com.example.tp_grupo6.entities.Feedback;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.repositories.FeedbackRepository;
import com.example.tp_grupo6.repositories.UsuarioRepository;
import com.example.tp_grupo6.services.FeedbackService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FeedbackDto createFeedback(FeedbackDto feedbackDto) {
        // Verificar que el usuario existe
        Usuario usuario = usuarioRepository.findById(feedbackDto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + feedbackDto.getUsuarioId()));
        
        // Convertir DTO a entidad
        Feedback feedback = new Feedback();
        feedback.setMensaje(feedbackDto.getMensaje());
        feedback.setPuntuacion(feedbackDto.getPuntuacion());
        feedback.setCategoria(feedbackDto.getCategoria());
        feedback.setUsuario(usuario);
        feedback.setFechaCreacion(LocalDateTime.now());
        feedback.setProcesado(false);
        
        // Guardar en la base de datos
        Feedback savedFeedback = feedbackRepository.save(feedback);
        
        // Convertir entidad guardada a DTO
        return convertToDto(savedFeedback);
    }

    @Override
    public List<FeedbackDto> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAllOrderByFechaCreacionDesc();
        return feedbacks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> getPendingFeedbacks() {
        List<Feedback> pendingFeedbacks = feedbackRepository.findByProcesado(false);
        return pendingFeedbacks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> getFeedbackByCategory(String categoria) {
        List<Feedback> feedbacks = feedbackRepository.findByCategoria(categoria);
        return feedbacks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackDto getFeedbackById(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback no encontrado con ID: " + id));
        return convertToDto(feedback);
    }

    @Override
    public void markAsProcessed(Long id) {
        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback no encontrado con ID: " + id));
        feedback.setProcesado(true);
        feedbackRepository.save(feedback);
    }

    @Override
    public Map<String, Object> getFeedbackStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalFeedbacks = feedbackRepository.count();
        long pendingFeedbacks = feedbackRepository.findByProcesado(false).size();
        long processedFeedbacks = feedbackRepository.findByProcesado(true).size();
        Double averageRating = feedbackRepository.getPromedioPuntuacion();
        
        stats.put("totalFeedbacks", totalFeedbacks);
        stats.put("pendingFeedbacks", pendingFeedbacks);
        stats.put("processedFeedbacks", processedFeedbacks);
        stats.put("averageRating", averageRating != null ? averageRating : 0.0);
        
        return stats;
    }
    
    private FeedbackDto convertToDto(Feedback feedback) {
        FeedbackDto dto = new FeedbackDto();
        dto.setIdFeedback(feedback.getIdFeedback());
        dto.setMensaje(feedback.getMensaje());
        dto.setPuntuacion(feedback.getPuntuacion());
        dto.setCategoria(feedback.getCategoria());
        dto.setFechaCreacion(feedback.getFechaCreacion());
        dto.setProcesado(feedback.isProcesado());
        dto.setUsuarioId(feedback.getUsuario().getIdUsuario());
        dto.setNombreUsuario(feedback.getUsuario().getUsername());
        return dto;
    }
}