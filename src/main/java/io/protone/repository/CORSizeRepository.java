package io.protone.repository;

import io.protone.domain.CORSize;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORSize entity.
 */
@SuppressWarnings("unused")
public interface CORSizeRepository extends JpaRepository<CORSize,Long> {

}
