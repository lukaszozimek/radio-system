package io.protone.repository;

import io.protone.domain.CORArea;
import io.protone.domain.CORNetwork;
import io.protone.domain.TRACampaign;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRACampaign entity.
 */
@SuppressWarnings("unused")
public interface TRACampaignRepository extends JpaRepository<TRACampaign, Long> {

    List<TRACampaign> findByNetwork(CORNetwork network);

    TRACampaign findByNameAndNetwork(String name, CORNetwork network);
}
