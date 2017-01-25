package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.CRMLeadStatus;
import io.protone.domain.CRMOpportunity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMOpportunity entity.
 */
@SuppressWarnings("unused")
public interface CRMOpportunityRepository extends JpaRepository<CRMOpportunity,Long> {
    List<CRMOpportunity> findByNetwork(CORNetwork network);
    CRMOpportunity findByNameAndNetwork(String name,CORNetwork network);
}
