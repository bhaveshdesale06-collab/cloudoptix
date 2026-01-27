package com.cloudoptix.cloudoptix_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WasteFindingResponse {

    private Long id;
    private String wasteType;
    private String severity;
    private String description;
    private LocalDateTime detectedAt;
    private String cloudAccountName;
}
