package io.protone.repository;

import io.protone.domain.CorStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorStatus entity.
 */
@SuppressWarnings("unused")
public interface CorStatusRepository extends JpaRepository<CorStatus,Long> {

}
