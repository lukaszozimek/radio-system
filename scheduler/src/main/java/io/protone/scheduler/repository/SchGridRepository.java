package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchClock;
import io.protone.scheduler.domain.SchGrid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Grid repository.
 */
public interface SchGridRepository extends JpaRepository<SchGrid, Long> {

    void deleteByShortNameAndNetwork_ShortcutAndChannel_Shortcut(String shortName, String networkShortCut, String channelShortcut);

    Slice<SchGrid> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);

    SchGrid findOneByNetwork_ShortcutAndChannel_Shortcut(String shortName, String networkShortCut, String channelShortcut);

}
