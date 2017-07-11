package io.protone.library.repository;


import io.protone.library.domain.LibFileItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibFileItem entity.
 */
@SuppressWarnings("unused")
public interface LibFileItemRepository extends JpaRepository<LibFileItem,Long> {

}
