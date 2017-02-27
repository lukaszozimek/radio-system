package io.protone.repository;

import io.protone.domain.SchBlock;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the SchBlock entity.
 */
@SuppressWarnings("unused")
public interface SchBlockRepository extends JpaRepository<SchBlock,Long> {

}
