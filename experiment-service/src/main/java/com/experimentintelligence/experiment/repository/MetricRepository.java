package com.experimentintelligence.experiment.repository;

import com.experimentintelligence.experiment.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetricRepository extends JpaRepository<Metric, UUID> {
    
    @Query("SELECT m FROM Metric m WHERE m.run.experiment.organizationId = :orgId")
    List<Metric> findAllByOrganizationId(@Param("orgId") String orgId);

    List<Metric> findByRunId(UUID runId);
}
