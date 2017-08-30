package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchClockConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchClockConfigurationRepository extends JpaRepository<SchClockConfiguration, Long> {

    Slice<SchClockConfiguration> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);


    SchClockConfiguration findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);
}
