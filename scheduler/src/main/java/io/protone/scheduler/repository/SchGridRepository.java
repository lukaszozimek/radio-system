package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchGrid;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Grid repository.
 */
public interface SchGridRepository extends JpaRepository<SchGrid, Long> {
}
