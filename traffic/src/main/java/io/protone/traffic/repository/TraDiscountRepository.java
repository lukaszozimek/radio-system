package io.protone.traffic.repository;

import io.protone.core.domain.CorNetwork;
import io.protone.traffic.domain.TraDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Spring Data JPA repository for the TraDiscount entity.
 */
@SuppressWarnings("unused")
public interface TraDiscountRepository extends JpaRepository<TraDiscount,Long> {

    List<TraDiscount> findByNetwork(CorNetwork corNetwork);

    TraDiscount findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}

