package io.protone.core.repository;


import io.protone.core.domain.CorTag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the CorTag entity.
 */
@SuppressWarnings("unused")
public interface CorTagRepository extends JpaRepository<CorTag,Long> {

}
