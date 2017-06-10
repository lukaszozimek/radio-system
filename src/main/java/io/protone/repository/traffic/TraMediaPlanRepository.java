package io.protone.repository.traffic;

import io.protone.domain.TraMediaPlan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraMediaPlan entity.
 */
@SuppressWarnings("unused")
public interface TraMediaPlanRepository extends JpaRepository<TraMediaPlan,Long> {

}
