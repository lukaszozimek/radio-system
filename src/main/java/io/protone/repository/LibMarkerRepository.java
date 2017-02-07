package io.protone.repository;

import io.protone.domain.LibMarker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibMarker entity.
 */
@SuppressWarnings("unused")
public interface LibMarkerRepository extends JpaRepository<LibMarker,Long> {

}
