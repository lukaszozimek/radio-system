package io.protone.repository;

import io.protone.domain.SchPlaylist;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SchPlaylist entity.
 */
@SuppressWarnings("unused")
public interface SchPlaylistRepository extends JpaRepository<SchPlaylist,Long> {

}