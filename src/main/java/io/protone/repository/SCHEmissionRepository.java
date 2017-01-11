package io.protone.repository;

import io.protone.domain.SCHEmission;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SCHEmission entity.
 */
@SuppressWarnings("unused")
public interface SCHEmissionRepository extends JpaRepository<SCHEmission,Long> {

}
