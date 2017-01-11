package io.protone.repository;

import io.protone.domain.TRAEmissionOrder;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRAEmissionOrder entity.
 */
@SuppressWarnings("unused")
public interface TRAEmissionOrderRepository extends JpaRepository<TRAEmissionOrder,Long> {

}
