package io.protone.repository;

import io.protone.domain.TraIndustry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraIndustry entity.
 */
@SuppressWarnings("unused")
public interface TraIndustryRepository extends JpaRepository<TraIndustry,Long> {

}
