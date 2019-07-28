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

    Slice<SchClockTemplate> findAllByNetwork_ShortcutAndChannel_ShortcutAndType(String networkShortCut, String channelShortcut, String type, Pageable pageable);


    SchClockTemplate findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(@Param("network") String networkShortCut, @Param("channelShortcut") String channelShortcut, @Param("shortName") String shortName);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);

    Slice<SchClockTemplate> findAllByNetwork_ShortcutAndChannel_ShortcutAndClockCategory_Name(String networkShortcut, String channelShortcut, String categoryName, Pageable pageable);
}
