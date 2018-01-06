package io.protone.library.repository;


import io.protone.library.domain.LibMediaItem;
import io.protone.library.domain.LibMediaLibrary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the LibMediaItem entity.
 */
@SuppressWarnings("unused")
public interface LibMediaItemRepository extends JpaRepository<LibMediaItem, Long> {
    List<LibMediaItem> findByLibrary(LibMediaLibrary library);

    Slice<LibMediaItem> findSliceByChannel_Organization_ShortcutAndChannel_ShortcutAndLibrary_Shortcut(String organizationShortcut, String channelShortcut, String libraryDB, Pageable pageable);

    Optional<LibMediaItem> findByChannel_Organization_ShortcutAndChannel_ShortcutAndLibrary_ShortcutAndIdx(String organizationShortcut, String channelShortcut, String libraryDB, String idx);

}
