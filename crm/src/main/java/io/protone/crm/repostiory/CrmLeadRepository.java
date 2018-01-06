package io.protone.crm.repostiory;

import io.protone.core.domain.CorNetwork;
import io.protone.crm.domain.CrmLead;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the CrmLead entity.
 */
public interface CrmLeadRepository extends JpaRepository<CrmLead, Long> {
    List<CrmLead> findByChannel_Organization_Shortcut(String network);

    Slice<CrmLead> findSliceByChannel_Organization_ShortcutAndChannel_Shortcut(String organization, String channelShortcut, Pageable pageable);

    CrmLead findOneByShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortcut, String organization, String channelShortcut);

    void deleteByShortnameAndChannel_Organization_ShortcutAndChannel_Shortcut(String shortcut, String organization, String channelShortcut);
}
