package io.protone.repository;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraOrder entity.
 */
@SuppressWarnings("unused")
public interface TraOrderRepository extends JpaRepository<TraOrder, Long> {
    List<TraOrder> findByNetwork(CorNetwork network);

    List<TraOrder> findByCustomerAndNetwork(CrmAccount customer, CorNetwork corNetwork);
}
