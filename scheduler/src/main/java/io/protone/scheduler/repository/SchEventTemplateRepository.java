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

    void deleteByChannel_Organization_ShortcutAndChannel_ShortcutAndShortName(String organizationShortcut, String channelShortcut, String shortName);

    @Query("select event from SchEventTemplate as event " +
            "left join fetch event.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch event.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where org.shortcut = :organization  and ch.shortcut= :channelShortcut and event.instance =:instance and event.type =:type")
    Slice<SchEventTemplate> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndInstanceAndType(@Param("organization") String organizationShortcut, @Param("channelShortcut") String channelShortcut, @Param("instance") boolean instance, @Param("type") String type, Pageable pageable);

    @Query("select event from SchEventTemplate as event " +
            "left join fetch event.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch event.schLogConfiguration as logConf " +
            "left join fetch logConf.logColumns as logColumns " +
            "where org.shortcut = :organization and event.shortName =:shortName and ch.shortcut= :channelShortcut and event.instance =:instance  and event.type =:type")
    SchEventTemplate findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndShortNameAndInstanceAndType(@Param("organization") String organizationShortcut, @Param("channelShortcut") String channelShortcut, @Param("shortName") String shortName, @Param("instance") boolean instance, @Param("type") String type);

    Slice<SchEventTemplate> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndEventCategory_NameAndInstanceAndType(String organizationShortcut, String channelShortcut, String categoryName, boolean instance, String type, Pageable pageable);
}
