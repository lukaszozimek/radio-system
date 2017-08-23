package io.protone.library.repository;


import io.protone.library.domain.LibLibrary;
import io.protone.library.domain.LibMediaItem;
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
    List<LibMediaItem> findByLibrary(LibLibrary library);

    Slice<LibMediaItem> findSliceByNetwork_ShortcutAndLibrary_Shortcut(String networkShortcut, String libraryDB, Pageable pageable);

    Optional<LibMediaItem> findByNetwork_ShortcutAndLibrary_ShortcutAndIdx(String networkShortcut, String libraryDB, String idx);

}
