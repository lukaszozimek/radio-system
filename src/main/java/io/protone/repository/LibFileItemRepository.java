package io.protone.repository;

import io.protone.domain.LibFileItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibFileItem entity.
 */
@SuppressWarnings("unused")
public interface LibFileItemRepository extends JpaRepository<LibFileItem,Long> {

}
