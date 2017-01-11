package io.protone.repository;

import io.protone.domain.TRAOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRAOrder entity.
 */
@SuppressWarnings("unused")
public interface TRAOrderRepository extends JpaRepository<TRAOrder,Long> {

}
