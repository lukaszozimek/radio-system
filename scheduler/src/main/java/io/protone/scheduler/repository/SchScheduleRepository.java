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
    Slice<SchSchedule> findAllByNetwork_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut, Pageable pageable);

    SchSchedule findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(String organizationShortcut, String channelShortcut, LocalDate date);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndDate(String organizationShortcut, String channelShortcut, LocalDate date);


    @Query("select schedule  from SchSchedule as schedule " +
            "left join fetch schedule.network as n " +
            "left join fetch schedule.channel as c " +
            "where n.shortcut = :network  AND c.shortcut = :channelShortcut AND  schedule.date BETWEEN :fromDate  AND :endDate ")
    List<SchSchedule> findAllByNetwork_ShortcutAndChannel_ShortcutAndDateBetween(@Param("network") String shortcut, @Param("channelShortcut") String channelShortcut, @Param("fromDate") LocalDate from, @Param("endDate") LocalDate to);

}
