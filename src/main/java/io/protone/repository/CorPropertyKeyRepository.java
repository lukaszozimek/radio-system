package io.protone.repository;

import io.protone.domain.CorPropertyKey;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorPropertyKey entity.
 */
@SuppressWarnings("unused")
public interface CorPropertyKeyRepository extends JpaRepository<CorPropertyKey,Long> {

}
