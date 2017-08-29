package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * A Playlist repository.
 */
public interface SchLogRepository extends JpaRepository<SchLog, Long> {

    Slice<SchLog> findAllByNetwork_ShortcutAndChannel_ShortcutAndSchLogConfiguration_Extension(String networkShortcut, String channelShortcut, String configurationExtension, Pageable pageable);

    SchLog findOneByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(String networkShortcut, String channelShortcut, LocalDate date, String configurationExtension);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(String networkShortcut, String channelShortcut, LocalDate date, String configurationExtension);
}
