package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * A Playlist repository.
 */
public interface SchLogRepository extends JpaRepository<SchLog, Long> {


    @Query("select log from SchLog as log " +
            "left join fetch log.network as n " +
            "left join fetch log.channel as ch " +
            "left join fetch log.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where n.shortcut = :network  and ch.shortcut= :channelShortcut and logConf.extension=:extension")
    Slice<SchLog> findAllByNetwork_ShortcutAndChannel_ShortcutAndSchLogConfiguration_Extension(@Param("network") String organizationShortcut, @Param("channelShortcut") String channelShortcut, @Param("extension") String extension, Pageable pageable);

    @Query("select log from SchLog as log " +
            "left join fetch log.network as n " +
            "left join fetch log.channel as ch " +
            "left join fetch log.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where n.shortcut = :network  and ch.shortcut= :channelShortcut and log.date= :date and logConf.extension=:extension")
    SchLog findOneByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(@Param("network") String organizationShortcut, @Param("channelShortcut") String channelShortcut, @Param("date") LocalDate date, @Param("extension") String configurationExtension);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndDateAndSchLogConfiguration_Extension(String organizationShortcut, String channelShortcut, LocalDate date, String configurationExtension);
}
