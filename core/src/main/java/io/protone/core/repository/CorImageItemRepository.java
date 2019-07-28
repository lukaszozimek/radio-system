package io.protone.core.repository;


import io.protone.core.domain.CorImageItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LibImageItem entity.
 */
@SuppressWarnings("unused")
public interface CorImageItemRepository extends JpaRepository<CorImageItem,Long> {

}
