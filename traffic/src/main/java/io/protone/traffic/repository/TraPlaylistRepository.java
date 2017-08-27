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
            "left join fetch p.network as n " +
            " where n.shortcut = :network")
    List<TraPlaylist> findAllByNetwork_Shortcut(@Param("network") String shortcut, Pageable pageable);

    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.network as n " +
            "left join fetch p.channel as c " +
            "left join fetch p.playlists as b " +
            " where n.shortcut = :network AND c.shortcut = :channelShortcut")
    Slice<TraPlaylist> findSliceByNetwork_ShortcutAndChannel_Shortcut(@Param("network") String shortcut, @Param("channelShortcut") String channelShortcut, Pageable pageable);

    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.network as n " +
            "left join fetch p.channel as c " +
            "left join fetch p.playlists as b " +
            " where n.shortcut = :network AND c.shortcut = :channelShortcut AND p.playlistDate BETWEEN :fromDate  AND :endDate ")
    List<TraPlaylist> findAllByNetwork_ShortcutAndChannel_ShortcutAndPlaylistDateBetween(@Param("network") String shortcut, @Param("channelShortcut") String channelShortcut, @Param("fromDate") LocalDate from, @Param("endDate") LocalDate to);


    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.network as n " +
            "left join fetch p.channel as c " +
            "left join fetch p.playlists as b " +
            " where n.shortcut = :network AND  p.playlistDate BETWEEN :fromDate  AND :endDate ")
    List<TraPlaylist> findAllByNetwork_ShortcutAndPlaylistDateBetween(@Param("network") String shortcut, @Param("fromDate") LocalDate from, @Param("endDate") LocalDate to);

    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.network as n " +
            "left join fetch p.channel as c " +
            "left join fetch p.playlists as b " +
            " where n.shortcut = :network AND c.shortcut = :channelShortcut and p.id =:id")
    TraPlaylist findOneByIdAndNetwork_ShortcutAndChannel_Shortcut(@Param("id") Long id, @Param("network") String shortcut, @Param("channelShortcut") String channelShortcut);

    @Query("select p  from TraPlaylist as p " +
            "left join fetch p.network as n " +
            "left join fetch p.channel as c " +
            "left join fetch p.playlists as b " +
            " where n.shortcut = :network AND c.shortcut = :channelShortcut and p.playlistDate =:date")
    TraPlaylist findOneByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(@Param("date")LocalDate date, @Param("network")String shortcut, @Param("channelShortcut")String channelShortcut);


    void deleteByPlaylistDateAndNetwork_Shortcut(LocalDate date, String shortcut);

    void deleteByPlaylistDateAndNetwork_ShortcutAndChannel_Shortcut(LocalDate date, String shortcut, String channelShortcut);

}
