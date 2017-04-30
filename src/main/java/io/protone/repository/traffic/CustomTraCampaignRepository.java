package io.protone.repository.traffic;

import io.protone.domain.CorNetwork;
import io.protone.domain.CrmAccount;
import io.protone.domain.TraCampaign;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraCampaign entity.
 */
@SuppressWarnings("unused")
public interface CustomTraCampaignRepository extends JpaRepository<TraCampaign, Long> {
    List<TraCampaign> findByNetwork(CorNetwork network);

    List<TraCampaign> findAllByNetwork(CorNetwork network, Pageable pageable);

    TraCampaign findByNameAndNetwork(String name, CorNetwork network);

    List<TraCampaign> findByCustomerAndNetwork(CrmAccount crmAccount, CorNetwork corNetwork);

    void deleteByNameAndNetwork(String shortcut, CorNetwork corNetwork);
}
