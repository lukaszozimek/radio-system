package io.protone.scheduler.repository;

import io.protone.core.domain.enumeration.CorDayOfWeekEnum;
import io.protone.scheduler.domain.SchGrid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Grid repository.
 */
public interface SchGridRepository extends JpaRepository<SchGrid, Long> {

    Slice<SchGrid> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);

    SchGrid findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);


    SchGrid findOneByNetwork_ShortcutAndChannel_ShortcutAndDefaultGridAndDayOfWeek(String networkShortcut, String channelShortcut, Boolean defaultGrid, CorDayOfWeekEnum corDayOfWeekEnum);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);
}
