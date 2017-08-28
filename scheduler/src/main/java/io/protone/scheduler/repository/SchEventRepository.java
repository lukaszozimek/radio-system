package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * An Event repository.
 */
public interface SchEventRepository extends JpaRepository<SchEvent, Long> {
    void deleteByShortNameAndNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortCut, String channelShortcut, String shortName);

    Slice<SchEvent> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);

    SchEvent findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortCut, String channelShortcut, String shortName);
}
