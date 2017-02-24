package io.protone.repository;

import io.protone.domain.CrmOpportunity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmOpportunity entity.
 */
@SuppressWarnings("unused")
public interface CrmOpportunityRepository extends JpaRepository<CrmOpportunity,Long> {

}