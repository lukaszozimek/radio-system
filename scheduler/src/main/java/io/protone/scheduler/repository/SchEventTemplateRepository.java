package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEventTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchEventTemplateRepository extends JpaRepository<SchEventTemplate, Long> {

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortCut, String channelShortcut, String shortName);

    @Query("select event from SchEventTemplate as event " +
            "left join fetch event.network as n " +
            "left join fetch event.channel as ch " +
            "left join fetch event.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where n.shortcut = :network  and ch.shortcut= :channelShortcut and event.instance =:instance and event.type =:type")
    Slice<SchEventTemplate> findAllByNetwork_ShortcutAndChannel_ShortcutAndInstanceAndType(@Param("network") String networkShortCut, @Param("channelShortcut") String channelShortcut, @Param("instance") boolean instance, @Param("type") String type, Pageable pageable);

    @Query("select event from SchEventTemplate as event " +
            "left join fetch event.network as n " +
            "left join fetch event.channel as ch " +
            "left join fetch event.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where n.shortcut = :network and event.shortName =:shortName and ch.shortcut= :channelShortcut and event.instance =:instance  and event.type =:type")
    SchEventTemplate findOneByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndInstanceAndType(@Param("network") String networkShortCut, @Param("channelShortcut") String channelShortcut, @Param("shortName") String shortName, @Param("instance") boolean instance, @Param("type") String type);

    Slice<SchEventTemplate> findAllByNetwork_ShortcutAndChannel_ShortcutAndEventCategory_NameAndInstanceAndType(String networkShortcut, String channelShortcut, String categoryName, boolean instance, String type, Pageable pageable);
}
