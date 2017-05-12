package io.protone.repository.library;

import io.protone.domain.LibVideoObject;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the LibVideoObject entity.
 */
@SuppressWarnings("unused")
public interface LibVideoObjectRepository extends JpaRepository<LibVideoObject,Long> {

}
