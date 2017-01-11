package io.protone.repository;

import io.protone.domain.LIBTrack;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBTrack entity.
 */
@SuppressWarnings("unused")
public interface LIBTrackRepository extends JpaRepository<LIBTrack,Long> {

}
