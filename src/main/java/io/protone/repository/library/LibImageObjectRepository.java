package io.protone.repository.library;

import io.protone.domain.LibImageObject;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibImageObject entity.
 */
@SuppressWarnings("unused")
public interface LibImageObjectRepository extends JpaRepository<LibImageObject,Long> {

}
