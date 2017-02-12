package io.protone.repository;

import io.protone.domain.CfgMarkerConfiguration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CfgMarkerConfiguration entity.
 */
@SuppressWarnings("unused")
public interface CfgMarkerConfigurationRepository extends JpaRepository<CfgMarkerConfiguration,Long> {

}
