package io.protone.repository;

import io.protone.domain.CRMStage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMStage entity.
 */
@SuppressWarnings("unused")
public interface CRMStageRepository extends JpaRepository<CRMStage, Long> {
    CRMStage findByName(String name);
}
