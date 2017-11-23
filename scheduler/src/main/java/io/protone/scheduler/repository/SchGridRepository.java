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

    Slice<SchGrid> findAllByNetwork_ShortcutAndChannel_ShortcutAndType(String networkShortCut, String channelShortcut, String eventType, Pageable pageable);

    Slice<SchGrid> findAllByNetwork_ShortcutAndChannel_ShortcutAndGridCategory_NameAndType(String networkShortCut, String channelShortcut, String categoryName, String eventType, Pageable pageable);

    Slice<SchGrid> findOneByNetwork_ShortcutAndChannel_ShortcutAndDefaultGridAndType(String networkShortcut, String channelShortcut, Boolean defaultGrid, String eventType, Pageable pageable);

    SchGrid findOneByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndType(String networkShortcut, String channelShortcut, String shortName, String eventType);


    SchGrid findOneByNetwork_ShortcutAndChannel_ShortcutAndDefaultGridAndDayOfWeekAndType(String networkShortcut, String channelShortcut, Boolean defaultGrid, CorDayOfWeekEnum corDayOfWeekEnum, String eventType);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndShortNameAndType(String networkShortcut, String channelShortcut, String shortName, String eventType);
}
