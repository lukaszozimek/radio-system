package io.protone.crm.repostiory;

import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraDiscount entity.
 */
@SuppressWarnings("unused")
public interface CrmDiscountRepository extends JpaRepository<CrmDiscount, Long> {

    List<CrmDiscount> findByNetwork(CorNetwork corNetwork);

    CrmDiscount findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}

