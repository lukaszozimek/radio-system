package io.protone.repository;

import io.protone.domain.CRMTaskStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMTaskStatus entity.
 */
@SuppressWarnings("unused")
public interface CRMTaskStatusRepository extends JpaRepository<CRMTaskStatus,Long> {

}
