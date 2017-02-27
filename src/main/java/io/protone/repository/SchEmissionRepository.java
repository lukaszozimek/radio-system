package io.protone.repository;

import io.protone.domain.SchEmission;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the SchEmission entity.
 */
@SuppressWarnings("unused")
public interface SchEmissionRepository extends JpaRepository<SchEmission,Long> {

}
