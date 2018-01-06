package io.protone.traffic.repository;


import io.protone.traffic.domain.TraPlaylist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the TraPlaylist entity.
 */
@SuppressWarnings("unused")
public interface TraPlaylistRepository extends JpaRepository<TraPlaylist, Long> {
    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch p.playlists as b " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization")
    Slice<TraPlaylist> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch p.playlists as b " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization AND p.playlistDate BETWEEN :fromDate  AND :endDate ")
    List<TraPlaylist> findAllByChannel_Organization_ShortcutAndChannel_ShortcutAndPlaylistDateBetween(@Param("organization") String organization, @Param("channelShortcut") String channelShortcut, @Param("fromDate") LocalDate from, @Param("endDate") LocalDate to);

    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch p.playlists as b " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and p.id =:id")
    TraPlaylist findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);

    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.channel as ch " +
            "left join fetch ch.organization as org " +
            "left join fetch p.playlists as b " +
            " where ch.shortcut = :channelShortcut and org.shortcut =:organization and p.playlistDate =:date")
    TraPlaylist findOneByPlaylistDateAndChannel_Organization_ShortcutAndChannel_Shortcut(@Param("date") LocalDate date, @Param("organization") String organization, @Param("channelShortcut") String channelShortcut);


    void deleteByPlaylistDateAndChannel_Organization_ShortcutAndChannel_Shortcut(LocalDate date, String organization, String channelShortcut);

}
