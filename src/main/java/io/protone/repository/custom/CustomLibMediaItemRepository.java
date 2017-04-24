package io.protone.repository.custom;

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
public interface CustomLibMediaItemRepository extends JpaRepository<LibMediaItem, Long> {
    List<LibMediaItem> findByLibrary(LibLibrary library);

    List<LibMediaItem> findByLibrary(LibLibrary library, Pageable pageable);

    Optional<LibMediaItem> findByLibraryAndIdx(LibLibrary libraryDB, String idx);
}
