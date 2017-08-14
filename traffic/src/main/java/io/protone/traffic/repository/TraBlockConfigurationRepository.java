package io.protone.traffic.repository;


import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.traffic.domain.TraBlockConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraBlockConfiguration entity.
 */
@SuppressWarnings("unused")
public interface TraBlockConfigurationRepository extends JpaRepository<TraBlockConfiguration, Long> {
    List<TraBlockConfiguration> findAllByNetwork_Shortcut(@Param("network") String shortcut, Pageable pageable);

    List<TraBlockConfiguration> findAllByNetwork_ShortcutAndDay(String shortcut, CorDayOfWeekEnum dayOfWeekEnum);

    TraBlockConfiguration findOneByIdAndNetwork_Shortcut(@Param("id") Long id, @Param("network") String shortcut);

    void deleteByIdAndNetwork_Shortcut(Long id, String shortcut);
}
