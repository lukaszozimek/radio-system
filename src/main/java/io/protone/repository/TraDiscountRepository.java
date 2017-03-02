package io.protone.repository;

import io.protone.domain.TraDiscount;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the TraDiscount entity.
 */
@SuppressWarnings("unused")
public interface TraDiscountRepository extends JpaRepository<TraDiscount,Long> {

}
