package io.protone.scheduler.repository;

import io.protone.library.domain.LibMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A MediaItem repository.
 */
public interface SchMediaItemRepository extends JpaRepository<LibMediaItem, Long> {
}
