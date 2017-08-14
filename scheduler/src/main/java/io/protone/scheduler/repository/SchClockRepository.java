package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchClock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * A Clock repository.
 */
public interface SchClockRepository extends JpaRepository<SchClock, Long> {

    void deleteByShortNameAndNetwork_ShortcutAndChannel_Shortcut(String shortName, String networkShortCut, String channelShortcut);

    List<SchClock> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortCut, String channelShortcut, Pageable pageable);

    List<SchClock> findOneByNetwork_ShortcutAndChannel_Shortcut(String shortName, String networkShortCut, String channelShortcut);
}
