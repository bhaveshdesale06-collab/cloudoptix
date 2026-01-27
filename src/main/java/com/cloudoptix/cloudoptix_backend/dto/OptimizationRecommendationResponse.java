package com.cloudoptix.cloudoptix_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OptimizationRecommendationResponse {

    private Long id;
    private String actionType;
    private String recommendation;
    private double estimatedMonthlySavings;
    private LocalDateTime createdAt;
    private String cloudAccountName;
}
