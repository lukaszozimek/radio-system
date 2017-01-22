package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.TRACampaign;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRACampaign entity.
 */
@SuppressWarnings("unused")
public interface TRACampaignRepository extends JpaRepository<TRACampaign, Long> {
    TRACampaign findByName(String name);
}
