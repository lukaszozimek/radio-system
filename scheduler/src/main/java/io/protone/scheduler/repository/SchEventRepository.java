package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An Event repository.
 */
public interface SchEventRepository extends JpaRepository<SchEvent, Long> {
}
