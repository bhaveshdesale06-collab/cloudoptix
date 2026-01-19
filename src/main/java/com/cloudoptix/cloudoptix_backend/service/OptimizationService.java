package com.cloudoptix.cloudoptix_backend.service;

import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import com.cloudoptix.cloudoptix_backend.entity.WasteFinding;
import com.cloudoptix.cloudoptix_backend.entity.OptimizationRecommendation;
import com.cloudoptix.cloudoptix_backend.repository.WasteFindingRepository;
import com.cloudoptix.cloudoptix_backend.repository.OptimizationRecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OptimizationService {

    private final WasteFindingRepository wasteFindingRepository;
    private final OptimizationRecommendationRepository recommendationRepository;

    public void generateRecommendations(CloudAccount cloudAccount) {

        List<WasteFinding> findings =
                wasteFindingRepository.findByCloudAccount(cloudAccount);

        for (WasteFinding finding : findings) {

            switch (finding.getWasteType()) {

                case "IDLE" -> createRecommendation(
                        cloudAccount,
                        "SHUTDOWN",
                        "Shut down idle resources to avoid unnecessary cost",
                        5000
                );

                case "OVER_PROVISIONED" -> createRecommendation(
                        cloudAccount,
                        "DOWNSIZE",
                        "Downsize resource to a smaller instance",
                        3000
                );

                case "UNDER_UTILIZED" -> createRecommendation(
                        cloudAccount,
                        "UPGRADE",
                        "Upgrade memory to handle high workload efficiently",
                        0
                );
            }
        }
    }

    private void createRecommendation(
            CloudAccount cloudAccount,
            String action,
            String message,
            double savings
    ) {

        OptimizationRecommendation recommendation =
                OptimizationRecommendation.builder()
                        .actionType(action)
                        .recommendation(message)
                        .estimatedMonthlySavings(savings)
                        .createdAt(LocalDateTime.now())
                        .cloudAccount(cloudAccount)
                        .build();

        recommendationRepository.save(recommendation);
    }
}
