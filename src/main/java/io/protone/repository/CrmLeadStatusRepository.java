package io.protone.repository;

import io.protone.domain.CrmLeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CrmLeadStatus entity.
 */
@SuppressWarnings("unused")
public interface CrmLeadStatusRepository extends JpaRepository<CrmLeadStatus,Long> {

}
