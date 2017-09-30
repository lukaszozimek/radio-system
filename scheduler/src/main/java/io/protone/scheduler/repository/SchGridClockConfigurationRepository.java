package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchGridClockConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Grid repository.
 */
public interface SchGridClockConfigurationRepository extends JpaRepository<SchGridClockConfiguration, Long> {

}
