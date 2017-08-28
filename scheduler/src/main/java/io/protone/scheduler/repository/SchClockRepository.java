package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchClock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Clock repository.
 */
public interface SchClockRepository extends JpaRepository<SchClock, Long> {


    Slice<SchClock> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);


    SchClock findOneByNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);

    void deleteByShortNameAndNetwork_ShortcutAndChannel_ShortcutAndShortName(String networkShortcut, String channelShortcut, String shortName);
}
