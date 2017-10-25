package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchGridClockTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Grid repository.
 */
public interface SchGridClockConfigurationRepository extends JpaRepository<SchGridClockTemplate, Long> {

}
