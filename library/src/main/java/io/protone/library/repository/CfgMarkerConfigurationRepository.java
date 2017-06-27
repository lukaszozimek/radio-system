package io.protone.library.repository;

import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CfgMarkerConfiguration entity.
 */
@SuppressWarnings("unused")
public interface CfgMarkerConfigurationRepository extends JpaRepository<CfgMarkerConfiguration, Long> {
    CfgMarkerConfiguration findOneByIdAndNetwork(Long id, CorNetwork network);

    List<CfgMarkerConfiguration> findByNetwork(CorNetwork network);
}
