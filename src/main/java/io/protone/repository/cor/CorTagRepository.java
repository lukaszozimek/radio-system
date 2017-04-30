package io.protone.repository.cor;

import io.protone.domain.CorTag;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the CorTag entity.
 */
@SuppressWarnings("unused")
public interface CorTagRepository extends JpaRepository<CorTag,Long> {

}
