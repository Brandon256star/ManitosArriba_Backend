package com.example.tp_grupo6.controllers;

import com.example.tp_grupo6.dtos.FeedbackDto;
import com.example.tp_grupo6.services.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // User Story #18512331: Endpoint para que los usuarios puedan dejar feedback
    @PostMapping("/submit")
    public ResponseEntity<?> submitFeedback(@Valid @RequestBody FeedbackDto feedbackRequest) {
        try {
            FeedbackDto savedFeedback = feedbackService.createFeedback(feedbackRequest);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Gracias por tu feedback. Nos ayuda a mejorar la aplicación.",
                "feedbackId", savedFeedback.getIdFeedback()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", "Error al procesar el feedback: " + e.getMessage()
            ));
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks() {
        List<FeedbackDto> feedbacks = feedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FeedbackDto>> getPendingFeedbacks() {
        List<FeedbackDto> feedbacks = feedbackService.getPendingFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getFeedbackStats() {
        Map<String, Object> stats = feedbackService.getFeedbackStats();
        return ResponseEntity.ok(stats);
    }

    @PutMapping("/{id}/process")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> processFeedback(@PathVariable Long id) {
        try {
            feedbackService.markAsProcessed(id);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Feedback marcado como procesado"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "error", "Error al procesar feedback: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/by-category/{categoria}")
    public ResponseEntity<List<FeedbackDto>> getFeedbackByCategory(@PathVariable String categoria) {
        List<FeedbackDto> feedbacks = feedbackService.getFeedbackByCategory(categoria);
        return ResponseEntity.ok(feedbacks);
    }
}