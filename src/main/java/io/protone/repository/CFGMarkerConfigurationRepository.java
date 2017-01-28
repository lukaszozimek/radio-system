package io.protone.repository;

import io.protone.domain.CFGMarkerConfiguration;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CFGMarkerConfiguration entity.
 */
@SuppressWarnings("unused")
public interface CFGMarkerConfigurationRepository extends JpaRepository<CFGMarkerConfiguration,Long> {

    List<CFGMarkerConfiguration> findByNetwork(CORNetwork network);
}
