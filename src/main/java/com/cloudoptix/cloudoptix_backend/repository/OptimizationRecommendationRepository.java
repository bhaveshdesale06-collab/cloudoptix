package com.cloudoptix.cloudoptix_backend.repository;

import com.cloudoptix.cloudoptix_backend.entity.OptimizationRecommendation;
import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptimizationRecommendationRepository
        extends JpaRepository<OptimizationRecommendation, Long> {

    List<OptimizationRecommendation> findByCloudAccount(CloudAccount cloudAccount);
}
