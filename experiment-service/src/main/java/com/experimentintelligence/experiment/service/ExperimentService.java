package com.experimentintelligence.experiment.service;

import com.experimentintelligence.experiment.dto.CreateExperimentRequest;
import com.experimentintelligence.experiment.dto.ExperimentResponse;
import com.experimentintelligence.experiment.entity.Experiment;
import com.experimentintelligence.experiment.repository.ExperimentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExperimentService {

    private final ExperimentRepository experimentRepository;

    @Transactional
    public ExperimentResponse createExperiment(CreateExperimentRequest request, String organizationId, String userId) {
        Experiment experiment = Experiment.builder()
                .name(request.getName())
                .description(request.getDescription())
                .organizationId(organizationId)
                .createdBy(userId)
                .status("DRAFT")
                .build();

        Experiment savedExperiment = experimentRepository.save(experiment);

        return mapToResponse(savedExperiment);
    }

    private ExperimentResponse mapToResponse(Experiment experiment) {
        return ExperimentResponse.builder()
                .id(experiment.getId())
                .organizationId(experiment.getOrganizationId())
                .name(experiment.getName())
                .description(experiment.getDescription())
                .status(experiment.getStatus())
                .createdBy(experiment.getCreatedBy())
                .createdAt(experiment.getCreatedAt())
                .build();
    }
}
