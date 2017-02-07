package io.protone.repository;

import io.protone.domain.CrmLeadSource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CrmLeadSource entity.
 */
@SuppressWarnings("unused")
public interface CrmLeadSourceRepository extends JpaRepository<CrmLeadSource,Long> {

}
