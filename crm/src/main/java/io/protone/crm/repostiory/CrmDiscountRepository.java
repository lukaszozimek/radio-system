package io.protone.crm.repostiory;

import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmDiscount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TraDiscount entity.
 */
@SuppressWarnings("unused")
public interface CrmDiscountRepository extends JpaRepository<CrmDiscount, Long> {

    Slice<CrmDiscount> findSliceByNetwork(CorNetwork corNetwork, Pageable pageable);

    CrmDiscount findOneByIdAndNetwork(Long id, CorNetwork corNetwork);
}

