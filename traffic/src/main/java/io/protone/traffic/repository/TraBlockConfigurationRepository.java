package io.protone.traffic.repository;


import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.traffic.domain.TraBlockConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraBlockConfiguration entity.
 */
@SuppressWarnings("unused")
public interface TraBlockConfigurationRepository extends JpaRepository<TraBlockConfiguration, Long> {
    List<TraBlockConfiguration> findAllByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut);

    List<TraBlockConfiguration> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndDay(String organization, String channelShortcut, CorDayOfWeekEnum dayOfWeekEnum);

    TraBlockConfiguration findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organization, String channelShortcut);
}
