package io.protone.repository.cfg;

import io.protone.domain.CfgMarkerConfiguration;
import io.protone.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CfgMarkerConfiguration entity.
 */
@SuppressWarnings("unused")
public interface CustomCfgMarkerConfigurationRepository extends JpaRepository<CfgMarkerConfiguration, Long> {
    CfgMarkerConfiguration findOneByIdAndNetwork(Long id, CorNetwork network);

    List<CfgMarkerConfiguration> findByNetwork(CorNetwork network);
}
