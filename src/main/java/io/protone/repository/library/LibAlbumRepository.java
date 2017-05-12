package io.protone.repository.library;

import io.protone.domain.CorNetwork;
import io.protone.domain.LibAlbum;

import io.protone.domain.LibArtist;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibAlbum entity.
 */
@SuppressWarnings("unused")
public interface LibAlbumRepository extends JpaRepository<LibAlbum, Long> {

    LibAlbum findOneByNameAndArtistAndNetwork(String name, LibArtist artist, CorNetwork network);
}
