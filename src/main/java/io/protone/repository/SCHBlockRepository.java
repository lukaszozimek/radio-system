package io.protone.repository;

import io.protone.domain.SCHBlock;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SCHBlock entity.
 */
@SuppressWarnings("unused")
public interface SCHBlockRepository extends JpaRepository<SCHBlock,Long> {

}
