package io.protone.crm.repostiory;


import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmOpportunity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmOpportunity entity.
 */
public interface CrmOpportunityRepository extends JpaRepository<CrmOpportunity, Long> {
    List<CrmOpportunity> findByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut);

    void deleteByNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String name, String organization, String channelShortcut);

    Slice<CrmOpportunity> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    CrmOpportunity findOneByNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String name, String organization, String channelShortcut);

    CrmOpportunity findOneByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortcut, String organization, String channelShortcut);

    void deleteByShortNameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortcut, String organization, String channelShortcut);
}
