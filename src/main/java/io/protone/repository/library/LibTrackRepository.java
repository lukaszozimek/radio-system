package io.protone.repository.library;

import io.protone.domain.LibTrack;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibTrack entity.
 */
@SuppressWarnings("unused")
public interface LibTrackRepository extends JpaRepository<LibTrack,Long> {

}
