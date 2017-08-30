package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEventConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchEventConfigurationRepository extends JpaRepository<SchEventConfiguration, Long> {
    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortCut, String channelShortcut, String shortName);

    Slice<SchEventConfiguration> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);

    SchEventConfiguration findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortCut, String channelShortcut, String shortName);
}
