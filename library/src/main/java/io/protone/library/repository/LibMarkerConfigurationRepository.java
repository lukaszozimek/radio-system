package io.protone.library.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMarkerConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibMarkerConfiguration entity.
 */
@SuppressWarnings("unused")
public interface LibMarkerConfigurationRepository extends JpaRepository<LibMarkerConfiguration, Long> {
    LibMarkerConfiguration findOneByIdAndNetwork(Long id, CorNetwork network);

    List<LibMarkerConfiguration> findByNetwork(CorNetwork network);
}
