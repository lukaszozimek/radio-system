package io.protone.repository.traffic;

import io.protone.domain.TraEmission;
import io.protone.domain.TraPlaylist;

import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the TraPlaylist entity.
 */
@SuppressWarnings("unused")
public interface TraPlaylistRepository extends JpaRepository<TraPlaylist, Long> {
    List<TraPlaylist> findAllByNetwork_Shortcut(String shortcut, Pageable pageable);

    List<TraPlaylist> findAllByNetwork_ShortcutAndPlaylistDateBetween(String shortcut, LocalDate from, LocalDate to);

    TraPlaylist findOneByIdAndNetwork_Shortcut(Long id, String shortcut);

    TraPlaylist findOneByPlaylistDateAndNetwork_Shortcut(LocalDate date, String shortcut);


    void deleteByPlaylistDateAndNetwork_Shortcut(LocalDate date, String shortcut);

}
