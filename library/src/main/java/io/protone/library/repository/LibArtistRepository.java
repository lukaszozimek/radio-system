package io.protone.library.repository;


import io.protone.core.domain.CorChannel;
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
    LibArtist findOneByNameAndChannel(String name, CorChannel channel);

    Slice<LibArtist> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organizationShortcut, String channelShortcut, Pageable pagable);

    LibArtist findOneByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organizationShortcut, String channelShortcut);

    void deleteByIdAndChannel_Organization_ShortcutAndChannel_Shortcut(Long id, String organizationShortcut, String channelShortcut);
}
