package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraCampaign entity.
 */
@SuppressWarnings("unused")
public interface TraCampaignRepository extends JpaRepository<TraCampaign, Long> {
    List<TraCampaign> findByNetwork(CorNetwork network);

    TraCampaign findByNameAndNetwork(String name, CorNetwork network);
}
