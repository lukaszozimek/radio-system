package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.LibArtist;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibArtist entity.
 */
@SuppressWarnings("unused")
public interface LibArtistRepository extends JpaRepository<LibArtist, Long> {
    LibArtist findOneByNameAndNetwork(String name, CorNetwork corNetwork);

}
