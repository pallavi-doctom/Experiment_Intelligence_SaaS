package com.experimentintelligence.experiment.repository;

import com.experimentintelligence.experiment.entity.ExperimentRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExperimentRunRepository extends JpaRepository<ExperimentRun, UUID> {
    
    @Query("SELECT er FROM ExperimentRun er WHERE er.experiment.organizationId = :orgId")
    List<ExperimentRun> findAllByOrganizationId(@Param("orgId") String orgId);

    @Query("SELECT er FROM ExperimentRun er WHERE er.id = :id AND er.experiment.organizationId = :orgId")
    Optional<ExperimentRun> findByIdAndOrganizationId(@Param("id") UUID id, @Param("orgId") String orgId);

    List<ExperimentRun> findByExperimentId(UUID experimentId);
}
