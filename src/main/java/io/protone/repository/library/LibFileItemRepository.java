package io.protone.repository.library;

import io.protone.domain.LibFileItem;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibFileItem entity.
 */
@SuppressWarnings("unused")
public interface LibFileItemRepository extends JpaRepository<LibFileItem,Long> {

}
