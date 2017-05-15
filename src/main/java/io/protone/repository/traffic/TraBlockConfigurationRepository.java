package io.protone.repository.traffic;

import io.protone.domain.TraBlock;
import io.protone.domain.TraBlockConfiguration;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraBlockConfiguration entity.
 */
@SuppressWarnings("unused")
public interface TraBlockConfigurationRepository extends JpaRepository<TraBlockConfiguration,Long> {
    List<TraBlockConfiguration> findAllByNetwork_Shortcut(String shortcut, Pageable pageable);

    TraBlockConfiguration findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);
}
