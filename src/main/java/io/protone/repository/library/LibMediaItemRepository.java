package io.protone.repository.library;

import io.protone.domain.LibLibrary;
import io.protone.domain.LibMediaItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the LibMediaItem entity.
 */
@SuppressWarnings("unused")
public interface LibMediaItemRepository extends JpaRepository<LibMediaItem, Long> {
    List<LibMediaItem> findByLibrary(LibLibrary library);

    List<LibMediaItem> findByNetwork_ShortcutAndLibrary_Shortcut(String networkShortcut, String libraryDB, Pageable pageable);

    Optional<LibMediaItem> findByLibrary_ShortcutAndIdx(String libraryDB, String idx);
}
