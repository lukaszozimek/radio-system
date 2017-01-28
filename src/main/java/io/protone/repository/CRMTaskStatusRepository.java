package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CRMTaskStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMTaskStatus entity.
 */
@SuppressWarnings("unused")
public interface CRMTaskStatusRepository extends JpaRepository<CRMTaskStatus,Long> {

    List<CRMTaskStatus> findByNetwork(CORNetwork network);
}
