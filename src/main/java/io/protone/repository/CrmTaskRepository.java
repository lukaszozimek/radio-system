package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmContact;
import io.protone.domain.CrmTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmTask entity.
 */
@SuppressWarnings("unused")
public interface CrmTaskRepository extends JpaRepository<CrmTask, Long> {
    CrmTask findOneByCrmContactAndIdAndNetwork(CrmContact contact, Long id, CorNetwork network);

    List<CrmTask> findByCrmContactAndNetwork(CrmContact contact, CorNetwork network);

    void deleteByCrmContactAndIdAndNetwork(CrmContact contact, Long id, CorNetwork network);
}
