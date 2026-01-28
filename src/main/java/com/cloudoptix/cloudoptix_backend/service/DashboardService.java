package com.cloudoptix.cloudoptix_backend.service;

import com.cloudoptix.cloudoptix_backend.dto.DashboardSummaryResponse;
import com.cloudoptix.cloudoptix_backend.dto.OptimizationRecommendationResponse;
import com.cloudoptix.cloudoptix_backend.dto.ReportSummaryResponse;
import com.cloudoptix.cloudoptix_backend.dto.WasteFindingResponse;
import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import com.cloudoptix.cloudoptix_backend.repository.CloudAccountRepository;
import com.cloudoptix.cloudoptix_backend.repository.MetricRepository;
import com.cloudoptix.cloudoptix_backend.repository.WasteFindingRepository;
import com.cloudoptix.cloudoptix_backend.repository.OptimizationRecommendationRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;



import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final CloudAccountRepository cloudAccountRepository;
    private final MetricRepository metricRepository;
    private final WasteFindingRepository wasteFindingRepository;
    private final OptimizationRecommendationRepository recommendationRepository;

    public DashboardSummaryResponse getSummary() {

        double totalSavings =
                recommendationRepository.findAll()
                        .stream()
                        .mapToDouble(r -> r.getEstimatedMonthlySavings())
                        .sum();

        return DashboardSummaryResponse.builder()
                .totalCloudAccounts(cloudAccountRepository.count())
                .totalMetrics(metricRepository.count())
                .totalWasteFindings(wasteFindingRepository.count())
                .totalRecommendations(recommendationRepository.count())
                .totalEstimatedMonthlySavings(totalSavings)
                .build();
    }

    public List<WasteFindingResponse> getAllWaste() {

    return wasteFindingRepository.findAll()
            .stream()
            .map(waste -> WasteFindingResponse.builder()
                    .id(waste.getId())
                    .wasteType(waste.getWasteType())
                    .severity(waste.getSeverity())
                    .description(waste.getDescription())
                    .detectedAt(waste.getDetectedAt())
                    .cloudAccountName(
                            waste.getCloudAccount().getAccountName()
                    )
                    .build())
            .collect(Collectors.toList());
}

public List<OptimizationRecommendationResponse> getAllRecommendations() {

    return recommendationRepository.findAll()
            .stream()
            .map(rec -> OptimizationRecommendationResponse.builder()
                    .id(rec.getId())
                    .actionType(rec.getActionType())
                    .recommendation(rec.getRecommendation())
                    .estimatedMonthlySavings(rec.getEstimatedMonthlySavings())
                    .createdAt(rec.getCreatedAt())
                    .cloudAccountName(
                            rec.getCloudAccount().getAccountName()
                    )
                    .build())
            .collect(Collectors.toList());
}

public List<WasteFindingResponse> getWasteByCloudAccount(Long cloudAccountId) {

    CloudAccount account = cloudAccountRepository.findById(cloudAccountId)
            .orElseThrow(() -> new RuntimeException("Cloud account not found"));

    return wasteFindingRepository.findByCloudAccount(account)
            .stream()
            .map(waste -> WasteFindingResponse.builder()
                    .id(waste.getId())
                    .wasteType(waste.getWasteType())
                    .severity(waste.getSeverity())
                    .description(waste.getDescription())
                    .detectedAt(waste.getDetectedAt())
                    .cloudAccountName(account.getAccountName())
                    .build())
            .toList();
}

public List<OptimizationRecommendationResponse> getRecommendationsByCloudAccount(
        Long cloudAccountId
) {

    CloudAccount account = cloudAccountRepository.findById(cloudAccountId)
            .orElseThrow(() -> new RuntimeException("Cloud account not found"));

    return recommendationRepository.findByCloudAccount(account)
            .stream()
            .map(rec -> OptimizationRecommendationResponse.builder()
                    .id(rec.getId())
                    .actionType(rec.getActionType())
                    .recommendation(rec.getRecommendation())
                    .estimatedMonthlySavings(rec.getEstimatedMonthlySavings())
                    .createdAt(rec.getCreatedAt())
                    .cloudAccountName(account.getAccountName())
                    .build())
            .toList();
}

public List<WasteFindingResponse> getWasteByAccountAndTime(
        Long cloudAccountId,
        LocalDateTime from,
        LocalDateTime to
) {

    CloudAccount account = cloudAccountRepository.findById(cloudAccountId)
            .orElseThrow(() -> new RuntimeException("Cloud account not found"));

    return wasteFindingRepository
            .findByCloudAccountAndDetectedAtBetween(account, from, to)
            .stream()
            .map(waste -> WasteFindingResponse.builder()
                    .id(waste.getId())
                    .wasteType(waste.getWasteType())
                    .severity(waste.getSeverity())
                    .description(waste.getDescription())
                    .detectedAt(waste.getDetectedAt())
                    .cloudAccountName(account.getAccountName())
                    .build())
            .toList();
}

public List<OptimizationRecommendationResponse> getRecommendationsByAccountAndTime(
        Long cloudAccountId,
        LocalDateTime from,
        LocalDateTime to
) {

    CloudAccount account = cloudAccountRepository.findById(cloudAccountId)
            .orElseThrow(() -> new RuntimeException("Cloud account not found"));

    return recommendationRepository
            .findByCloudAccountAndCreatedAtBetween(account, from, to)
            .stream()
            .map(rec -> OptimizationRecommendationResponse.builder()
                    .id(rec.getId())
                    .actionType(rec.getActionType())
                    .recommendation(rec.getRecommendation())
                    .estimatedMonthlySavings(rec.getEstimatedMonthlySavings())
                    .createdAt(rec.getCreatedAt())
                    .cloudAccountName(account.getAccountName())
                    .build())
            .toList();
}
public ReportSummaryResponse getReportSummary(
        Long cloudAccountId,
        LocalDateTime from,
        LocalDateTime to
) {

    CloudAccount account = cloudAccountRepository.findById(cloudAccountId)
            .orElseThrow(() -> new RuntimeException("Cloud account not found"));

    long wasteCount = wasteFindingRepository
            .findByCloudAccountAndDetectedAtBetween(account, from, to)
            .size();

    var recommendations =
            recommendationRepository
                    .findByCloudAccountAndCreatedAtBetween(account, from, to);

    double totalSavings = recommendations.stream()
            .mapToDouble(r -> r.getEstimatedMonthlySavings())
            .sum();

    return ReportSummaryResponse.builder()
            .totalWasteFindings(wasteCount)
            .totalRecommendations(recommendations.size())
            .totalEstimatedSavings(totalSavings)
            .build();
}

}
