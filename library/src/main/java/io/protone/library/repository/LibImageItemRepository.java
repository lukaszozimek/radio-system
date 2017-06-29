package io.protone.library.repository;


import io.protone.library.domain.LibImageItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibImageItem entity.
 */
@SuppressWarnings("unused")
public interface LibImageItemRepository extends JpaRepository<LibImageItem,Long> {

}
