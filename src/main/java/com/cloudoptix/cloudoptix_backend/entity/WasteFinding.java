package com.cloudoptix.cloudoptix_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "waste_findings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WasteFinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // IDLE, UNDER_UTILIZED, OVER_PROVISIONED
    private String wasteType;

    // Human-readable explanation
    private String description;

    // LOW, MEDIUM, HIGH
    private String severity;

    private LocalDateTime detectedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cloud_account_id", nullable = false)
    private CloudAccount cloudAccount;
}
