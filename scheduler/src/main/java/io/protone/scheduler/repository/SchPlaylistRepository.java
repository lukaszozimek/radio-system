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

    Slice<SchPlaylist> findAllByChannel_Organization_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut, Pageable pageable);


    @Query("select play from SchPlaylist as play " +
            "left join fetch play.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch play.emissions as emi " +
            "where org.shortcut = :organization  and ch.shortcut= :channelShortcut and play.date=:datePlaylist")
    SchPlaylist findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndDate(@Param("organization") String organizationShortcut, @Param("channelShortcut") String channelShortcut, @Param("datePlaylist") LocalDate date);

    @Query("select play from SchPlaylist as play " +
            "left join fetch play.channel as ch " +
            "left join fetch ch.organization as org " +
            "where org.shortcut = :organization  and ch.shortcut= :channelShortcut and play.date=:datePlaylist")
    SchPlaylist findOne(@Param("organization") String organizationShortcut, @Param("channelShortcut") String channelShortcut, @Param("datePlaylist") LocalDate date);

    void deleteByChannel_Organization_ShortcutAndChannel_ShortcutAndDate(String organizationShortcut, String channelShortcut, LocalDate date);
}
