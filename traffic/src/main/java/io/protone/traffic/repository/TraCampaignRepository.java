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

    Slice<TraCampaign> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    TraCampaign findByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortName, String organization, String channelShortcut);

    Slice<TraCampaign> findSliceByCustomer_ShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String crmAccount, String organization, String channelShortcut, Pageable pageable);

    void deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortcut, String organization, String channelShortcut);

    void deleteByCustomerAndChannel_Organization_ShortcutAndChannel_Shortcut(CrmAccount crmAccount, String organization, String channelShortcut);

}
