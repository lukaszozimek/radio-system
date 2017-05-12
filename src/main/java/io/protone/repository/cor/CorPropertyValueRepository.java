package io.protone.repository.cor;

import io.protone.domain.CorPropertyValue;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CorPropertyValue entity.
 */
@SuppressWarnings("unused")
public interface CorPropertyValueRepository extends JpaRepository<CorPropertyValue,Long> {

}
