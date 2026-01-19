package com.cloudoptix.cloudoptix_backend.service;

import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import com.cloudoptix.cloudoptix_backend.entity.Metric;
import com.cloudoptix.cloudoptix_backend.entity.WasteFinding;
import com.cloudoptix.cloudoptix_backend.repository.MetricRepository;
import com.cloudoptix.cloudoptix_backend.repository.WasteFindingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WasteDetectionService {

    private final MetricRepository metricRepository;
    private final WasteFindingRepository wasteFindingRepository;

    public void analyze(CloudAccount cloudAccount) {

        List<Metric> metrics = metricRepository.findByCloudAccount(cloudAccount);

        if (metrics.isEmpty()) return;

        double avgCpu = metrics.stream().mapToDouble(Metric::getCpuUsage).average().orElse(0);
        double avgMemory = metrics.stream().mapToDouble(Metric::getMemoryUsage).average().orElse(0);
        double avgNetwork = metrics.stream().mapToDouble(Metric::getNetworkUsage).average().orElse(0);

        //Rule 1: IDLE RESOURCE
        if (avgCpu < 10 && avgNetwork < 20) {
            saveFinding(
                    cloudAccount,
                    "IDLE",
                    "Resource is mostly idle with very low CPU and network usage",
                    "HIGH"
            );
        }
 


        // Rule 2: OVER PROVISIONED
        if (avgCpu < 20 && avgMemory < 30) {
            saveFinding(
                    cloudAccount,
                    "OVER_PROVISIONED",
                    "Resource is over-provisioned compared to usage",
                    "MEDIUM"
            );
        }

        // Rule 3: UNDER UTILIZED
        if (avgMemory > 90) {
            saveFinding(
                    cloudAccount,
                    "UNDER_UTILIZED",
                    "Memory usage is consistently high",
                    "LOW"
            );
        }
    }

    private void saveFinding(
            CloudAccount cloudAccount,
            String type,
            String description,
            String severity
    ) {
        WasteFinding finding = WasteFinding.builder()
                .wasteType(type)
                .description(description)
                .severity(severity)
                .detectedAt(LocalDateTime.now())
                .cloudAccount(cloudAccount)
                .build();

        wasteFindingRepository.save(finding);
    }
}
