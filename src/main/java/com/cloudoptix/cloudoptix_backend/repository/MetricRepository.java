package com.cloudoptix.cloudoptix_backend.repository;

import com.cloudoptix.cloudoptix_backend.entity.Metric;
import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long> {

    List<Metric> findByCloudAccount(CloudAccount cloudAccount);
}
