package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchPlaylist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * A Playlist repository.
 */
public interface SchPlaylistRepository extends JpaRepository<SchPlaylist, Long> {

    Slice<SchPlaylist> findAllByNetwork_ShortcutAndChannel_Shortcut(String networkShortcut, String channelShortcut, Pageable pageable);


    @Query("select play from SchPlaylist as play " +
            "left join fetch play.network as n " +
            "left join fetch play.channel as ch " +
            "left join fetch play.emissions as emi " +
            "where n.shortcut = :network  and ch.shortcut= :channelShortcut and play.date=:datePlaylist")
    SchPlaylist findOneByNetwork_ShortcutAndChannel_ShortcutAndDate(@Param("network") String networkShortCut, @Param("channelShortcut") String channelShortcut, @Param("datePlaylist") LocalDate date);

    void deleteByNetwork_ShortcutAndChannel_ShortcutAndDate(String networkShortcut, String channelShortcut, LocalDate date);
}
