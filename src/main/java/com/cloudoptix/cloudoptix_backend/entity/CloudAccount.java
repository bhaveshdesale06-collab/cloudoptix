package com.cloudoptix.cloudoptix_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cloud_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String provider;   // AWS, AZURE, GCP

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String accessType; // READ_ONLY

    @Column(nullable = false)
    private String createdBy;  // admin email

    private LocalDateTime createdAt;
}
