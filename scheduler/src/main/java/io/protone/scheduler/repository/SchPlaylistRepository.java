package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchPlaylist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * A Playlist repository.
 */
public interface SchPlaylistRepository extends JpaRepository<SchPlaylist, Long> {

    Slice<SchPlaylist> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortcut, String channelShortcut, Pageable pageable);

    SchPlaylist findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(String networkShortcut, String channelShortcut, LocalDate date);

    void deleteByShortNameAndNetwork_ShortcutAndChannel_ShortcutAndDate(String networkShortcut, String channelShortcut, LocalDate date);
}
