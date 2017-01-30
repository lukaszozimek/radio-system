package io.protone.repository;

import io.protone.domain.LIBMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LIBMediaItem entity.
 */
@SuppressWarnings("unused")
public interface LIBMediaItemRepository extends JpaRepository<LIBMediaItem,Long> {

}
