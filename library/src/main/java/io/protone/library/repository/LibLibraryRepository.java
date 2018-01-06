package io.protone.library.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.library.domain.LibMediaLibrary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the LibMediaLibrary entity.
 */
@SuppressWarnings("unused")
public interface LibLibraryRepository extends JpaRepository<LibMediaLibrary, Long> {
    LibMediaLibrary findOneByChannel_Organization_ShortcutAndChannel_ShortcutAndShortcut(String network, String corChannel, String shortcut);

    Slice<LibMediaLibrary> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String network, String channelShortcut, Pageable pageable);

    void deleteByChannel_Organization_ShortcutAndChannel_ShortcutAndShortcut(String organizationShortcut, String channelShortcut, String libraryShortcut);
}
