package io.protone.repository;

import io.protone.domain.CorRange;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CorRange entity.
 */
@SuppressWarnings("unused")
public interface CorRangeRepository extends JpaRepository<CorRange,Long> {

}