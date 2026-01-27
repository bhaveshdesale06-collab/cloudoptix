package com.cloudoptix.cloudoptix_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {

    private long totalCloudAccounts;
    private long totalMetrics;
    private long totalWasteFindings;
    private long totalRecommendations;
    private double totalEstimatedMonthlySavings;
}
