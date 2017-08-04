package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchTimeParams;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A TimeParams repository.
 */
public interface SchTimeParamsRepository extends JpaRepository<SchTimeParams, Long> {
}
