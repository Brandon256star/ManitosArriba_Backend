package com.example.tp_grupo6.services;

import com.example.tp_grupo6.dtos.FeedbackDto;

import java.util.List;
import java.util.Map;

public interface FeedbackService {
    
    FeedbackDto createFeedback(FeedbackDto feedbackDto);
    
    List<FeedbackDto> getAllFeedbacks();
    
    List<FeedbackDto> getPendingFeedbacks();
    
    List<FeedbackDto> getFeedbackByCategory(String categoria);
    
    FeedbackDto getFeedbackById(Long id);
    
    void markAsProcessed(Long id);
    
    Map<String, Object> getFeedbackStats();
}