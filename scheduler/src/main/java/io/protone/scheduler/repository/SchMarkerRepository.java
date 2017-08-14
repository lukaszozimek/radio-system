package io.protone.scheduler.repository;

import io.protone.library.domain.LibMarker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Marker repository.
 */
public interface SchMarkerRepository extends JpaRepository<LibMarker, Long> {
}
