package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A MediaItem repository.
 */
public interface SchMediaItemRepository extends JpaRepository<SchMediaItem, Long> {
}
