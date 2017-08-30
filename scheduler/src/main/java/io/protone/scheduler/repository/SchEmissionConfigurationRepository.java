package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEmissionConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An Emission repository.
 */
public interface SchEmissionConfigurationRepository extends JpaRepository<SchEmissionConfiguration, Long> {
}
