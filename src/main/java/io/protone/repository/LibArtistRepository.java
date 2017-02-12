package io.protone.repository;

import io.protone.domain.LibArtist;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibArtist entity.
 */
@SuppressWarnings("unused")
public interface LibArtistRepository extends JpaRepository<LibArtist,Long> {

}
