package io.protone.repository;

import io.protone.domain.LIBMarker;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBMarker entity.
 */
@SuppressWarnings("unused")
public interface LIBMarkerRepository extends JpaRepository<LIBMarker,Long> {

}
