package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchQueueParams;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A QueueParams repository.
 */
public interface SchQueueParamsRepository extends JpaRepository<SchQueueParams, Long> {
}
