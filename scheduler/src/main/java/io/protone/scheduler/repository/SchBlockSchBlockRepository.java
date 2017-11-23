package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchBlockSchBlock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Block repository.
 */
public interface SchBlockSchBlockRepository extends JpaRepository<SchBlockSchBlock, Long> {

    void deleteAllByPk_ParentTemplate_Id(Long id);
}
