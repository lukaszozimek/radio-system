package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEventConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchEventConfigurationRepository extends JpaRepository<SchEventConfiguration, Long> {

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortCut, String channelShortcut, String shortName);

    @Query("select event from SchEventConfiguration as event " +
            "left join fetch event.network as n " +
            "left join fetch event.channel as ch " +
            "left join fetch event.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where n.shortcut = :network  and ch.shortcut= :channelShortcut")
    Slice<SchEventConfiguration> findAllByNetwork_ShortcutAndChannel_Shortcut(@Param("network") String networkShortCut, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    @Query("select event from SchEventConfiguration as event " +
            "left join fetch event.network as n " +
            "left join fetch event.channel as ch " +
            "left join fetch event.emissions as eventE " +
            "left join fetch eventE.attachments as emissionAttachments " +
            "left join fetch event.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where n.shortcut = :network and event.shortName =:shortName and ch.shortcut= :channelShortcut")
    SchEventConfiguration findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(@Param("network") String networkShortCut, @Param("channelShortcut") String channelShortcut, @Param("shortName") String shortName);
}
