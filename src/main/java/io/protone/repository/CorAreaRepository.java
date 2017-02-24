package io.protone.repository;

import io.protone.domain.CorArea;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorArea entity.
 */
@SuppressWarnings("unused")
public interface CorAreaRepository extends JpaRepository<CorArea,Long> {

}