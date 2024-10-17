package com.micro_services.quiz_service.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private Integer noOfQuestions;
    private String title;
}
