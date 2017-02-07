package io.protone.repository;

import io.protone.domain.LibMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibMediaItem entity.
 */
@SuppressWarnings("unused")
public interface LibMediaItemRepository extends JpaRepository<LibMediaItem,Long> {

}
