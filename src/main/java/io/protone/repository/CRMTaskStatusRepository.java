package io.protone.repository;

import io.protone.domain.CFGTaskStatus;
import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CRMTaskStatus entity.
 */
@SuppressWarnings("unused")
public interface CRMTaskStatusRepository extends JpaRepository<CFGTaskStatus,Long> {

    List<CFGTaskStatus> findByNetwork(CORNetwork network);
}
