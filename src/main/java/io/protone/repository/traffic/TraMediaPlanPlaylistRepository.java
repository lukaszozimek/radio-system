package io.protone.repository.traffic;

import io.protone.domain.TraMediaPlanPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraMediaPlanPlaylist entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanPlaylistRepository extends JpaRepository<TraMediaPlanPlaylist,Long> {

}
