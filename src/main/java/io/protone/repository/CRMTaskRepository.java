package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.CRMStage;
import io.protone.domain.CRMTask;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMTask entity.
 */
@SuppressWarnings("unused")
public interface CRMTaskRepository extends JpaRepository<CRMTask,Long> {

    List<CRMTask> findByNetwork(CORNetwork network);
}
