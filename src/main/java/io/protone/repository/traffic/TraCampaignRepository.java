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
public interface TraCampaignRepository extends JpaRepository<TraCampaign, Long> {
    List<TraCampaign> findByNetwork(CorNetwork network);

    List<TraCampaign> findAllByNetwork_Shortcut(String network, Pageable pageable);

    TraCampaign findByNameAndNetwork_Shortcut(String name, String network);

    List<TraCampaign> findByCustomer_ShortNameAndNetwork_Shortcut(String crmAccount, String corNetwork, Pageable pageable);

    void deleteByNameAndNetwork_Shortcut(String shortcut, String corNetwork);

}
