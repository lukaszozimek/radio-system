package io.protone.repository;

import io.protone.domain.LIBLibrary;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBLibrary entity.
 */
@SuppressWarnings("unused")
public interface LIBLibraryRepository extends JpaRepository<LIBLibrary,Long> {

}
