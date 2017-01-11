package io.protone.repository;

import io.protone.domain.LIBAlbum;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBAlbum entity.
 */
@SuppressWarnings("unused")
public interface LIBAlbumRepository extends JpaRepository<LIBAlbum,Long> {

}
