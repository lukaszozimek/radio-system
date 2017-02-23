package io.protone.repository;

import io.protone.domain.LibAlbum;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibAlbum entity.
 */
@SuppressWarnings("unused")
public interface LibAlbumRepository extends JpaRepository<LibAlbum,Long> {

}
