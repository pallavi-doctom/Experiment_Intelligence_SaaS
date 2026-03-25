package com.experimentintelligence.experiment.repository;

import com.experimentintelligence.experiment.entity.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExperimentRepository extends JpaRepository<Experiment, UUID> {
    List<Experiment> findByOrganizationId(String organizationId);
    Optional<Experiment> findByIdAndOrganizationId(UUID id, String organizationId);
}
