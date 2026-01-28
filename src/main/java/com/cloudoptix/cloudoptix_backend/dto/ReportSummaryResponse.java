package com.cloudoptix.cloudoptix_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportSummaryResponse {

    private long totalWasteFindings;
    private long totalRecommendations;
    private double totalEstimatedSavings;
}
