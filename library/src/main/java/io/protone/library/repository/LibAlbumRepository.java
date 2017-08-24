package io.protone.library.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibAlbum;
import io.protone.library.domain.LibArtist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibAlbum entity.
 */
@SuppressWarnings("unused")
public interface LibAlbumRepository extends JpaRepository<LibAlbum, Long> {

    LibAlbum findOneByNameAndArtistAndNetwork(String name, LibArtist artist, CorNetwork network);

    Slice<LibAlbum> findSliceByNetwork_Shortcut(String networkShortcut, Pageable pagable);

    LibAlbum findOneByIdAndNetwork_Shortcut(Long id, String networkShortcut);
}
