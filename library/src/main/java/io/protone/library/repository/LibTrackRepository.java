package io.protone.library.repository;

import io.protone.domain.LibTrack;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibTrack entity.
 */
@SuppressWarnings("unused")
public interface LibTrackRepository extends JpaRepository<LibTrack,Long> {

}
