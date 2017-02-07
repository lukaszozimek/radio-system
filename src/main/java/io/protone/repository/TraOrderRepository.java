package io.protone.repository;

import io.protone.domain.TraOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraOrder entity.
 */
@SuppressWarnings("unused")
public interface TraOrderRepository extends JpaRepository<TraOrder,Long> {

}
