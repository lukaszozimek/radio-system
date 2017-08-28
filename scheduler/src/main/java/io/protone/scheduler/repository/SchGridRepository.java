package io.protone.scheduler.repository;

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

    void deleteByShortNameAndNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);
}
