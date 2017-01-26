package io.protone.repository;

import io.protone.domain.CORNetwork;
import io.protone.domain.CRMTask;
import io.protone.domain.TRADiscount;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TRADiscount entity.
 */
@SuppressWarnings("unused")
public interface TRADiscountRepository extends JpaRepository<TRADiscount,Long> {

    List<CRMTask> findByNetwork(CORNetwork network);
}
