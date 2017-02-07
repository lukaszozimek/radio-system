package io.protone.repository;

import io.protone.domain.TraIndustry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraIndustry entity.
 */
@SuppressWarnings("unused")
public interface TraIndustryRepository extends JpaRepository<TraIndustry,Long> {

}
