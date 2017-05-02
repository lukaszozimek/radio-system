package io.protone.repository.cfg;

import io.protone.domain.CfgMarkerConfiguration;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CfgMarkerConfiguration entity.
 */
@SuppressWarnings("unused")
public interface CfgMarkerConfigurationRepository extends JpaRepository<CfgMarkerConfiguration,Long> {

}