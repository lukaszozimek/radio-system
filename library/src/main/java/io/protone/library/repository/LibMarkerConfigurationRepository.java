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
    LibMarkerConfiguration findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organizationShortcut, String channelShortcut);

    List<LibMarkerConfiguration> findByChannel_Organization_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut);
}
