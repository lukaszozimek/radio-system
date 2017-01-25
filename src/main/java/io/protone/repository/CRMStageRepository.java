package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.CRMOpportunity;
import io.protone.domain.CRMStage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMStage entity.
 */
@SuppressWarnings("unused")
public interface CRMStageRepository extends JpaRepository<CRMStage, Long> {
    List<CRMStage> findByNetwork(CORNetwork network);
    CRMStage findByNameAndNetwork(String name,CORNetwork network);
}
