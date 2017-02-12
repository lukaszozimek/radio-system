package io.protone.repository;

import io.protone.domain.LibImageObject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LibImageObject entity.
 */
@SuppressWarnings("unused")
public interface LibImageObjectRepository extends JpaRepository<LibImageObject,Long> {

}
