package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchClock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Clock repository.
 */
public interface SchClockRepository extends JpaRepository<SchClock, Long> {
}
