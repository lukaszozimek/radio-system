package io.protone.traffic.repository;


import io.protone.traffic.domain.TraMediaPlanPlaylistDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the TraMediaPlanPlaylistDate entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanPlaylistDateRepository extends JpaRepository<TraMediaPlanPlaylistDate, Long> {

    TraMediaPlanPlaylistDate findOneByPlaylistDateAndChannel_Organization_ShortcutAndChannel_Shortcut(LocalDate date, String organizationShortcut, String channelShortcut);

    List<TraMediaPlanPlaylistDate> findAllByChannel_Organization_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut, Pageable pageable);

    void deleteByPlaylistDateAndChannel_Organization_ShortcutAndChannel_Shortcut(LocalDate date, String organizationShortcut, String channelShortcut);

    List<TraMediaPlanPlaylistDate> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndMediaPlan_Id(String organization, String channelShortcut, Long id);
}
