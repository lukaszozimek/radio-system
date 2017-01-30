package io.protone.repository;

import io.protone.domain.LIBLibrary;
import io.protone.domain.LIBMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the LIBMediaItem entity.
 */
@SuppressWarnings("unused")
public interface LIBMediaItemRepositoryEx extends JpaRepository<LIBMediaItem,Long> {
    List<LIBMediaItem> findByLibrary(LIBLibrary library);
    Optional<LIBMediaItem> findByLibraryAndIdx(LIBLibrary libraryDB, String idx);
}
