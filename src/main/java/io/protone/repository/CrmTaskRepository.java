package io.protone.repository;

import io.protone.domain.CrmTask;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CrmTask entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskRepository extends JpaRepository<CrmTask,Long> {

}
