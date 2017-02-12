package io.protone.repository;

import io.protone.domain.CorPropertyValue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorPropertyValue entity.
 */
@SuppressWarnings("unused")
public interface CorPropertyValueRepository extends JpaRepository<CorPropertyValue,Long> {

}
