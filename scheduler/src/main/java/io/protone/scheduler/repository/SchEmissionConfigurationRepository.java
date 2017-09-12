package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEmissionConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * An Emission repository.
 */
public interface SchEmissionConfigurationRepository extends JpaRepository<SchEmissionConfiguration, Long> {

    void deleteAllBySchEventConfiguration_Id(long eventConfigurationId);

    Set<SchEmissionConfiguration> findAllBySchEventConfiguration_Id(long eventConfigurationId);
}
