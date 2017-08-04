package io.protone.scheduler.repository;

import io.protone.scheduler.domain.SchEmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * An Emission repository.
 */
public interface SchEmissionRepository extends JpaRepository<SchEmission, Long> {
}
