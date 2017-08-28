package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchSchedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * A Schedule repository.
 */
public interface SchScheduleRepository extends JpaRepository<SchSchedule, Long> {
    Slice<SchSchedule> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortcut, String channelShortcut, Pageable pageable);

    SchSchedule findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(String networkShortcut, String channelShortcut, LocalDate date);

    void deleteByShortNameAndNetwork_ShortcutAndChannel_ShortcutAndDate(String networkShortcut, String channelShortcut, LocalDate date);
}
