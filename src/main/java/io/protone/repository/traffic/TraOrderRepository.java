package io.protone.repository.traffic;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraOrder entity.
 */
@SuppressWarnings("unused")
public interface TraOrderRepository extends JpaRepository<TraOrder, Long> {
    List<TraOrder> findByNetwork(CorNetwork network);

    List<TraOrder> findByNetwork_Shortcut(String network, Pageable pageable);

    List<TraOrder> findByCustomer_ShortNameAndNetwork_Shortcut(String crmAccount, String corNetwork, Pageable pageable);

    TraOrder findByIdAndNetwork_Shortcut(Long id, String corNetwork);

    void deleteByIdAndNetwork_Shortcut(Long id, String corNetwork);
}
