package io.protone.traffic.repository;


import io.protone.traffic.domain.TraMediaPlanPlaylist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the TraMediaPlanPlaylist entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanPlaylistRepository extends JpaRepository<TraMediaPlanPlaylist,Long> {

    TraMediaPlanPlaylist findOneByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(LocalDate date, String networkshortcut, String channelShortcut);

    List<TraMediaPlanPlaylist> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkshortcut, String channelShortcut, Pageable pageable);

    void deleteByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(LocalDate date, String networkshortcut, String channelShortcut);
}
