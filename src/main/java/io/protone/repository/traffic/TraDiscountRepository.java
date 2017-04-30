package io.protone.repository.traffic;

import io.protone.domain.CorNetwork;
import io.protone.domain.TraDiscount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraDiscount entity.
 */
@SuppressWarnings("unused")
public interface TraDiscountRepository extends JpaRepository<TraDiscount,Long> {

    List<TraDiscount> findByNetwork(CorNetwork corNetwork);

    TraDiscount findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}

