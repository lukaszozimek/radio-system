package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchSchedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * A Schedule repository.
 */
public interface SchScheduleRepository extends JpaRepository<SchSchedule, Long> {
    Slice<SchSchedule> findAllByChannel_Organization_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut, Pageable pageable);

    SchSchedule findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndDate(String organizationShortcut, String channelShortcut, LocalDate date);


    @Query("select schedule  from SchSchedule as schedule " +
            "left join fetch schedule.channel as ch " +
            "left join fetch ch.organization as org " +
            "where org.shortcut = :organization  AND ch.shortcut = :channelShortcut AND  schedule.date BETWEEN :fromDate  AND :endDate ")
    List<SchSchedule> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndDateBetween(@Param("organization") String shortcut, @Param("channelShortcut") String channelShortcut, @Param("fromDate") LocalDate from, @Param("endDate") LocalDate to);

}
