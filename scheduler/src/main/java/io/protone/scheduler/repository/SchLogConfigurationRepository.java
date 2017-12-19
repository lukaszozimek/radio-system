package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchLogConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * A Playlist repository.
 */
public interface SchLogConfigurationRepository extends JpaRepository<SchLogConfiguration, Long> {

    Slice<SchLogConfiguration> findAllByNetwork_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut, Pageable pageable);

    List<SchLogConfiguration> findAllByNetwork_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut);

    SchLogConfiguration findOneByNetwork_ShortcutAndChannel_ShortcutAndId(String organizationShortcut, String channelShortcut, Long id);

    SchLogConfiguration findOneByNetwork_ShortcutAndChannel_ShortcutAndExtension(String organizationShortcut, String channelShortcut, String extension);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndId(String organizationShortcut, String channelShortcut, Long id);
}
