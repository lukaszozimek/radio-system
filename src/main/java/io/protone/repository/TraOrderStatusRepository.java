package io.protone.repository;

import io.protone.domain.TraOrderStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraOrderStatus entity.
 */
@SuppressWarnings("unused")
public interface TraOrderStatusRepository extends JpaRepository<TraOrderStatus,Long> {

}
