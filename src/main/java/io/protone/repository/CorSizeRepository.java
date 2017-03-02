package io.protone.repository;

import io.protone.domain.CorSize;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CorSize entity.
 */
@SuppressWarnings("unused")
public interface CorSizeRepository extends JpaRepository<CorSize,Long> {

}
