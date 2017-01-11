package io.protone.repository;

import io.protone.domain.LIBImageItem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LIBImageItem entity.
 */
@SuppressWarnings("unused")
public interface LIBImageItemRepository extends JpaRepository<LIBImageItem,Long> {

}
