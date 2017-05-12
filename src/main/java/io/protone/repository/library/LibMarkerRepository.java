package io.protone.repository.library;

import io.protone.domain.LibMarker;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibMarker entity.
 */
@SuppressWarnings("unused")
public interface LibMarkerRepository extends JpaRepository<LibMarker,Long> {

}
