package io.protone.repository;

import io.protone.domain.CORRange;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORRange entity.
 */
@SuppressWarnings("unused")
public interface CORRangeRepository extends JpaRepository<CORRange,Long> {

}
