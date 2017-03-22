package io.protone.repository;

import io.protone.domain.CorCountry;
import io.protone.domain.CorNetwork;
import io.protone.domain.TraCampaingStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraCampaingStatus entity.
 */
@SuppressWarnings("unused")
public interface TraCampaingStatusRepository extends JpaRepository<TraCampaingStatus,Long> {

    TraCampaingStatus findOneByIdAndNetwork(Long id, CorNetwork corNetwork);

    List<TraCampaingStatus> findByNetwork(CorNetwork corNetwork);
}
