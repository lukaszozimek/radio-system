package io.protone.repository;

import io.protone.domain.LIBMediaItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBMediaItem entity.
 */
@SuppressWarnings("unused")
public interface LIBMediaItemRepository extends JpaRepository<LIBMediaItem,Long> {

}
