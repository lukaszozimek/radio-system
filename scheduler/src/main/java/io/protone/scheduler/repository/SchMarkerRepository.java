package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchMarker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A Marker repository.
 */
public interface SchMarkerRepository extends JpaRepository<SchMarker, Long> {
}
