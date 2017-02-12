package io.protone.repository;

import io.protone.domain.LibTrack;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibTrack entity.
 */
@SuppressWarnings("unused")
public interface LibTrackRepository extends JpaRepository<LibTrack,Long> {

}
