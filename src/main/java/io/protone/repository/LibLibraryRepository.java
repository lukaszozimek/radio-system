package io.protone.repository;

import io.protone.domain.LibLibrary;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibLibrary entity.
 */
@SuppressWarnings("unused")
public interface LibLibraryRepository extends JpaRepository<LibLibrary,Long> {

}
