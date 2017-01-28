package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.TRAIndustry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRAIndustry entity.
 */
@SuppressWarnings("unused")
public interface TRAIndustryRepository extends JpaRepository<TRAIndustry, Long> {
    TRAIndustry findByNameAndNetwork(String name, CORNetwork network);
}
