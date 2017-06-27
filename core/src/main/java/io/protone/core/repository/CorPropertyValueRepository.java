package io.protone.core.repository;

import io.protone.domain.CorPropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorPropertyValue entity.
 */
@SuppressWarnings("unused")
public interface CorPropertyValueRepository extends JpaRepository<CorPropertyValue,Long> {

}
