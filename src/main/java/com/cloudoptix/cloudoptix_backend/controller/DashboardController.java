package com.cloudoptix.cloudoptix_backend.controller;

import com.cloudoptix.cloudoptix_backend.dto.DashboardSummaryResponse;
import com.cloudoptix.cloudoptix_backend.dto.OptimizationRecommendationResponse;
import com.cloudoptix.cloudoptix_backend.dto.ReportSummaryResponse;
import com.cloudoptix.cloudoptix_backend.dto.WasteFindingResponse;
import com.cloudoptix.cloudoptix_backend.service.DashboardService;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;
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

@GetMapping("/waste/{cloudAccountId}")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public List<WasteFindingResponse> getWasteByAccount(
        @PathVariable Long cloudAccountId
) {
    return dashboardService.getWasteByCloudAccount(cloudAccountId);
}

@GetMapping("/recommendations/{cloudAccountId}")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public List<OptimizationRecommendationResponse> getRecommendationsByAccount(
        @PathVariable Long cloudAccountId
) {
    return dashboardService.getRecommendationsByCloudAccount(cloudAccountId);
}

@GetMapping("/waste/{cloudAccountId}/range")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public List<WasteFindingResponse> getWasteByAccountAndTime(
        @PathVariable Long cloudAccountId,
        @RequestParam LocalDateTime from,
        @RequestParam LocalDateTime to
) {
    return dashboardService.getWasteByAccountAndTime(cloudAccountId, from, to);
}
@GetMapping("/recommendations/{cloudAccountId}/range")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public List<OptimizationRecommendationResponse> getRecommendationsByAccountAndTime(
        @PathVariable Long cloudAccountId,
        @RequestParam LocalDateTime from,
        @RequestParam LocalDateTime to
) {
    return dashboardService.getRecommendationsByAccountAndTime(
            cloudAccountId, from, to
    );
}

@GetMapping("/report/{cloudAccountId}")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public ReportSummaryResponse getReportSummary(
        @PathVariable Long cloudAccountId,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime from,

        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime to
) {
    return dashboardService.getReportSummary(cloudAccountId, from, to);
}

}
