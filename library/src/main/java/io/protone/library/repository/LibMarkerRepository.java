package io.protone.library.repository;


import io.protone.library.domain.LibMarker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibMarker entity.
 */
@SuppressWarnings("unused")
public interface LibMarkerRepository extends JpaRepository<LibMarker,Long> {

}
