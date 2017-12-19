package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchClockTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by lukaszozimek on 30/08/2017.
 */
public interface SchClockTemplateRepository extends JpaRepository<SchClockTemplate, Long> {

    Slice<SchClockTemplate> findAllByNetwork_ShortcutAndChannel_ShortcutAndType(String organizationShortcut, String channelShortcut, String type, Pageable pageable);


    SchClockTemplate findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(@Param("network") String organizationShortcut, @Param("channelShortcut") String channelShortcut, @Param("shortName") String shortName);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String organizationShortcut, String channelShortcut, String shortName);

    Slice<SchClockTemplate> findAllByNetwork_ShortcutAndChannel_ShortcutAndClockCategory_Name(String organizationShortcut, String channelShortcut, String categoryName, Pageable pageable);
}
