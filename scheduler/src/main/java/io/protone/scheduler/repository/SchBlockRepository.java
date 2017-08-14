package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchBlock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Block repository.
 */
public interface SchBlockRepository extends JpaRepository<SchBlock, Long> {
}
