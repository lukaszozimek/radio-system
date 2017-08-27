package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Playlist repository.
 */
public interface SchPlaylistRepository extends JpaRepository<SchPlaylist, Long> {

}
