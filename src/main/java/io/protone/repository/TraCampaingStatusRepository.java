package io.protone.repository;

import io.protone.domain.TraCampaingStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraCampaingStatus entity.
 */
@SuppressWarnings("unused")
public interface TraCampaingStatusRepository extends JpaRepository<TraCampaingStatus,Long> {

}