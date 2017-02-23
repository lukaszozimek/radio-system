package io.protone.repository;

import io.protone.domain.LibImageItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibImageItem entity.
 */
@SuppressWarnings("unused")
public interface LibImageItemRepository extends JpaRepository<LibImageItem,Long> {

}
