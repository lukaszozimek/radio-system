package io.protone.repository;

import io.protone.domain.CorTag;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorTag entity.
 */
@SuppressWarnings("unused")
public interface CorTagRepository extends JpaRepository<CorTag,Long> {

}
