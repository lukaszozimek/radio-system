package io.protone.repository;

import io.protone.domain.CrmTaskStatus;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CrmTaskStatus entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskStatusRepository extends JpaRepository<CrmTaskStatus,Long> {

}
