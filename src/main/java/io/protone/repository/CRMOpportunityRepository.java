package io.protone.repository;

import io.protone.domain.CRMOpportunity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMOpportunity entity.
 */
@SuppressWarnings("unused")
public interface CRMOpportunityRepository extends JpaRepository<CRMOpportunity,Long> {

}
