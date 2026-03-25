package com.experimentintelligence.experiment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperimentResponse {
    private UUID id;
    private String organizationId;
    private String name;
    private String description;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
}
