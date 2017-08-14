package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Schedule repository.
 */
public interface SchScheduleRepository extends JpaRepository<SchSchedule, Long> {
}
