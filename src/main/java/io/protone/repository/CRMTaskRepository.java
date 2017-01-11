package io.protone.repository;

import io.protone.domain.CRMTask;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMTask entity.
 */
@SuppressWarnings("unused")
public interface CRMTaskRepository extends JpaRepository<CRMTask,Long> {

}
