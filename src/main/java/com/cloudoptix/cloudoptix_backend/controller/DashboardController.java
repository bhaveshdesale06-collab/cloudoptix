package com.cloudoptix.cloudoptix_backend.controller;

import com.cloudoptix.cloudoptix_backend.dto.DashboardSummaryResponse;
import com.cloudoptix.cloudoptix_backend.dto.OptimizationRecommendationResponse;
import com.cloudoptix.cloudoptix_backend.dto.WasteFindingResponse;
import com.cloudoptix.cloudoptix_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public DashboardSummaryResponse getSummary() {
        return dashboardService.getSummary();
    }

    @GetMapping("/waste")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public List<WasteFindingResponse> getWasteFindings() {
    return dashboardService.getAllWaste();
}
@GetMapping("/recommendations")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public List<OptimizationRecommendationResponse> getRecommendations() {
    return dashboardService.getAllRecommendations();
}


}
