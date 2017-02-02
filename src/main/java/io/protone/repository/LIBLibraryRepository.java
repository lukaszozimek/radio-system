package io.protone.repository;

import io.protone.domain.LibLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LIBLibrary entity.
 */
@SuppressWarnings("unused")
public interface LIBLibraryRepository extends JpaRepository<LibLibrary,Long> {

}
