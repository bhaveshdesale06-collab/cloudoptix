package com.cloudoptix.cloudoptix_backend.service;

import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import com.cloudoptix.cloudoptix_backend.entity.Metric;
import com.cloudoptix.cloudoptix_backend.repository.CloudAccountRepository;
import com.cloudoptix.cloudoptix_backend.repository.MetricRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MetricSchedulerService {

    private final CloudAccountRepository cloudAccountRepository;
    private final MetricRepository metricRepository;
    private final WasteDetectionService wasteDetectionService;


    private final Random random = new Random();

    // Runs every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void generateMetrics() {

        List<CloudAccount> accounts = cloudAccountRepository.findAll();

        for (CloudAccount account : accounts) {

            Metric metric = Metric.builder()
                    .cpuUsage(10 + random.nextDouble() * 80)
                    .memoryUsage(15 + random.nextDouble() * 70)
                    .networkUsage(50 + random.nextDouble() * 500)
                    .recordedAt(LocalDateTime.now())
                    .cloudAccount(account)
                    .build();

            metricRepository.save(metric);
            wasteDetectionService.analyze(account);

        }
        
    }
}
