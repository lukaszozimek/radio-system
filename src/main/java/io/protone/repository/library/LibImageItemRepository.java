package io.protone.repository.library;

import io.protone.domain.LibImageItem;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibImageItem entity.
 */
@SuppressWarnings("unused")
public interface LibImageItemRepository extends JpaRepository<LibImageItem,Long> {

}
