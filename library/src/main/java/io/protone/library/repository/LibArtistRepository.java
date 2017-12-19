package io.protone.library.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibArtist;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibArtist entity.
 */
@SuppressWarnings("unused")
public interface LibArtistRepository extends JpaRepository<LibArtist, Long> {
    LibArtist findOneByNameAndNetwork(String name, CorNetwork corNetwork);

    Slice<LibArtist> findSliceByNetwork_Shortcut(String organizationShortcut, Pageable pagable);

    LibArtist findOneByIdAndNetwork_Shortcut(Long id, String organizationShortcut);
    void deleteByIdAndNetwork_Shortcut(Long id, String organizationShortcut);
}
