package io.protone.repository.custom;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmTask entity.
 */
@SuppressWarnings("unused")
public interface CustomCrmTaskRepository extends JpaRepository<CrmTask, Long> {
    CrmTask findOneByIdAndNetwork(Long id, CorNetwork network);

    List<CrmTask> findByNetwork(CorNetwork network);

    void deleteByIdAndNetwork(Long id, CorNetwork network);
}
