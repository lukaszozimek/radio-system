package io.protone.repository;

import io.protone.domain.CORArea;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CORArea entity.
 */
@SuppressWarnings("unused")
public interface CORAreaRepository extends JpaRepository<CORArea, Long> {
    CORArea findByName(String name);
}
