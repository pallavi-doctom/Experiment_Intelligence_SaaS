package com.experimentintelligence.experiment.controller;

import com.experimentintelligence.experiment.dto.CreateExperimentRequest;
import com.experimentintelligence.experiment.dto.ExperimentResponse;
import com.experimentintelligence.experiment.security.UserPrincipal;
import com.experimentintelligence.experiment.service.ExperimentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experiments")
@RequiredArgsConstructor
public class ExperimentController {

    private final ExperimentService experimentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ENGINEER', 'ORG_ADMIN')")
    public ResponseEntity<ExperimentResponse> createExperiment(@Valid @RequestBody CreateExperimentRequest request) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        ExperimentResponse response = experimentService.createExperiment(
                request,
                principal.getOrganizationId(),
                principal.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
