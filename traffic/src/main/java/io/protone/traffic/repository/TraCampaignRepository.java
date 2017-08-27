package io.protone.traffic.repository;


import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmAccount;
import io.protone.traffic.domain.TraCampaign;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the TraCampaign entity.
 */
@SuppressWarnings("unused")
public interface TraCampaignRepository extends JpaRepository<TraCampaign, Long> {
    List<TraCampaign> findByNetwork(CorNetwork network);

    Slice<TraCampaign> findSliceByNetwork_Shortcut(String network, Pageable pageable);

    TraCampaign findByShortNameAndNetwork_Shortcut(String shortName, String network);

    Slice<TraCampaign> findSliceByCustomer_ShortNameAndNetwork_Shortcut(String crmAccount, String corNetwork, Pageable pageable);

    void deleteByShortNameAndNetwork_Shortcut(String shortcut, String corNetwork);
    void deleteByCustomerAndNetwork_Shortcut(CrmAccount crmAccount, String corNetwork);

}
