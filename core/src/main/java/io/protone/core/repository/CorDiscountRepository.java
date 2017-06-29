package io.protone.core.repository;

import io.protone.core.domain.CorDiscount;
import io.protone.core.domain.CorNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Spring Data JPA repository for the TraDiscount entity.
 */
@SuppressWarnings("unused")
public interface CorDiscountRepository extends JpaRepository<CorDiscount, Long> {

    List<CorDiscount> findByNetwork(CorNetwork corNetwork);

    CorDiscount findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}

